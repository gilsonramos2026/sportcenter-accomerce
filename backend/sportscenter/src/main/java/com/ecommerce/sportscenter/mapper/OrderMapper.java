package com.ecommerce.sportscenter.mapper;

import com.ecommerce.sportscenter.dto.OrderDto;
import com.ecommerce.sportscenter.dto.OrderResponse;
import com.ecommerce.sportscenter.entity.OrderAggregate.Order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring") // Permite injetar o Mapper usando @Autowired onde for necessário
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // Mapeamento de Entidade para DTO de Resposta
    @Mapping(source = "id", target = "id")
    @Mapping(source = "basketId", target = "basketId")
    @Mapping(source = "shippingAddress", target = "shippingAddress")
    @Mapping(source = "subTotal", target = "subTotal")
    @Mapping(source = "deliveryFee", target = "deliveryFee")
    // Correção Java 21/Long: Garante o cálculo usando a propriedade interna da entidade em centavos
    @Mapping(target = "total", expression = "java(order.getSubTotal() + order.getDeliveryFee())")
    @Mapping(source = "orderDate", target = "orderDate") // Mapeia a data real salva no banco
    @Mapping(source = "orderStatus", target = "orderStatus") // Mapeia o status vindo do banco
    OrderResponse orderToOrderResponse(Order order);

    // Mapeamento de DTO de Entrada para Entidade (Usado na Criação do Pedido)
    @Mapping(target = "id", ignore = true) // O banco gera o ID automaticamente
    @Mapping(source = "basketId", target = "basketId")
    @Mapping(source = "shippingAddress", target = "shippingAddress")
    @Mapping(source = "subTotal", target = "subTotal")
    @Mapping(source = "deliveryFee", target = "deliveryFee")
    @Mapping(source = "orderDate", target = "orderDate")
    // Se o status inicial for fixo na criação, mapeia a string para o Enum correspondente da entidade
    @Mapping(target = "orderStatus", expression = "com.ecommerce.sportscenter.entity.OrderAggregate.OrderStatus.PENDING")
    Order orderDtoToOrder(OrderDto orderDto);

    // Correção de Retorno: Mapeia uma lista de Entidades para uma lista de DTOs de Resposta (e não OrderDto)
    List<OrderResponse> ordersToOrderResponses(List<Order> orders);

    // Atualização de uma entidade existente a partir de dados de um DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    void updateOrderFromOrderDto(OrderDto orderDto, @MappingTarget Order order);
}
