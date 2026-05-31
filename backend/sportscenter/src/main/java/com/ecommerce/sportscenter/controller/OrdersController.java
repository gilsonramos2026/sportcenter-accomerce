package com.ecommerce.sportscenter.controller;

import com.ecommerce.sportscenter.dto.OrderDto;
import com.ecommerce.sportscenter.dto.OrderResponse;
import com.ecommerce.sportscenter.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Log4j2
@RequiredArgsConstructor // Injeção automática via Lombok para campos finais
public class OrdersController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer orderId) {
        log.info("Buscando pedido com ID: {}", orderId);
        OrderResponse order = orderService.getOrderById(orderId);
        
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        
        log.warn("Pedido com ID {} não foi localizado no Postgres", orderId);
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info("Listando todos os pedidos cadastrados.");
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<OrderResponse>> getAllOrdersPaged(Pageable pageable) {
        log.info("Listando pedidos de forma paginada.");
        Page<OrderResponse> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }
    
    @PostMapping
    public ResponseEntity<Integer> createOrder(@Valid @RequestBody OrderDto orderDto) {
        log.info("Iniciando a criação de um novo pedido para o carrinho: {}", orderDto.getBasketId());
        Integer orderId = orderService.createOrder(orderDto);
        
        if (orderId != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
        }
        
        log.error("Falha interna ao processar e salvar o pedido no banco de dados.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        log.info("Removendo pedido ID: {}", orderId);
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build(); // Status 204 No Content
    }
}