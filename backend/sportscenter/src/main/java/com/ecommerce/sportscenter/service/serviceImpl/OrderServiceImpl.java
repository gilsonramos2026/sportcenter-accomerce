package com.ecommerce.sportscenter.service.serviceImpl;


import com.ecommerce.sportscenter.dto.BasketItemResponse;
import com.ecommerce.sportscenter.dto.BasketResponse;
import com.ecommerce.sportscenter.dto.OrderDto;
import com.ecommerce.sportscenter.dto.OrderResponse;
import com.ecommerce.sportscenter.entity.OrderAggregate.Order;
import com.ecommerce.sportscenter.entity.OrderAggregate.OrderItem;
import com.ecommerce.sportscenter.entity.OrderAggregate.ProductItemOrdered;
import com.ecommerce.sportscenter.exception.ProductNotFoundException;
import com.ecommerce.sportscenter.mapper.OrderMapper;
import com.ecommerce.sportscenter.repository.BrandRepository;
import com.ecommerce.sportscenter.repository.OrderRepository;
import com.ecommerce.sportscenter.repository.TypeRepository;
import com.ecommerce.sportscenter.service.BasketService;
import com.ecommerce.sportscenter.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Log4j2
@Validated // Ativa a validação dos parâmetros definidos na interface OrderService
@RequiredArgsConstructor // Substitui o construtor manual gerando injeção via construtor final implicitamente
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BasketService basketService;


    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(OrderMapper.INSTANCE::OrderToOrderResponse)
                .orElseThrow(() -> new ProductNotFoundException("Pedido com ID " + orderId + " não encontrado em nossa base de dados."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        // Usando as melhorias do Java 16+ para transformar streams em List diretamente com .toList()
        return orders.stream()
                .map(OrderMapper.INSTANCE::OrderToOrderResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderMapper.INSTANCE::OrderToOrderResponse);
    }

    @Override
    @Transactional
    public void deleteOrder(Integer orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ProductNotFoundException("Impossível deletar. Pedido com ID " + orderId + " não existe.");
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional
    // Garante atomicidade no banco do Postgres: se a deleção do carrinho falhar, o pedido sofre rollback
    public Integer createOrder(OrderDto orderDto) {
        log.info("Iniciando criação de pedido para o carrinho ID: {}", orderDto.getBasketId());

        // Buscando detalhes do carrinho no Redis
        BasketResponse basketResponse = basketService.getBasketById(orderDto.getBasketId());
        if (basketResponse == null) {
            log.error("Falha ao criar pedido: Carrinho com ID {} não foi localizado", orderDto.getBasketId());
            throw new ProductNotFoundException("Carrinho de compras não encontrado ou expirado.");
        }

        // Mapeamento dos itens usando streams eficientes
        List<OrderItem> orderItems = basketResponse.getItems().stream()
                .map(this::mapBasketItemToOrderItem)
                .toList();

        // Cálculo do subtotal unificado com a precisão matemática do Postgres (convertido para o tipo numérico do modelo)
        double subTotal = basketResponse.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Mapeamento e vinculação relacional
        Order order = OrderMapper.INSTANCE.orderResponseToOrder(orderDto);
        order.setOrderItems(orderItems);
        order.setSubTotal(subTotal);

        // Salvando no PostgreSQL e limpando o cache do Redis
        Order savedOrder = orderRepository.save(order);
        basketService.deleteBasketById(orderDto.getBasketId());
        
        log.info("Pedido criado com sucesso! ID gerado: {}", savedOrder.getId());
        return savedOrder.getId();
    }

    private OrderItem mapBasketItemToOrderItem(BasketItemResponse basketItemResponse) {
        if (basketItemResponse == null) return null;

        OrderItem orderItem = new OrderItem();
        orderItem.setItemOrdered(mapBasketItemToProduct(basketItemResponse));
        orderItem.setQuantity(basketItemResponse.getQuantity());
        return orderItem;
    }

    private ProductItemOrdered mapBasketItemToProduct(BasketItemResponse basketItemResponse) {
        ProductItemOrdered productItemOrdered = new ProductItemOrdered();
        productItemOrdered.setName(basketItemResponse.getName());
        productItemOrdered.setPictureUrl(basketItemResponse.getPictureUrl());
        productItemOrdered.setProductId(basketItemResponse.getId());
        return productItemOrdered;
    }
}
