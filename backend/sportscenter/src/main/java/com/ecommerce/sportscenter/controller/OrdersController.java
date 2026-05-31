package com.ecommerce.sportscenter.controller;

import com.ecommerce.sportscenter.dto.OrderDto;
import com.ecommerce.sportscenter.dto.OrderResponse;
import com.ecommerce.sportscenter.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Endpoints para gerenciamento e fechamento de pedidos")
public class OrdersController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    @Operation(summary = "Busca os detalhes de um pedido pelo ID")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer orderId) {
        log.info("Buscando pedido com ID: {}", orderId);
        OrderResponse order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        log.warn("Pedido ID {} não localizado na base Postgres", orderId);
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Lista todos os pedidos fechados no sistema")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info("Buscando lista total de pedidos.");
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/paged")
    @Operation(summary = "Lista todos os pedidos com suporte a paginação nativa")
    public ResponseEntity<Page<OrderResponse>> getAllOrdersPaged(Pageable pageable) {
        log.info("Buscando pedidos de forma paginada.");
        Page<OrderResponse> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }
    
    @PostMapping
    @Operation(summary = "Fecha e consolida um novo pedido baseado no carrinho ativo")
    public ResponseEntity<Integer> createOrder(@Valid @RequestBody OrderDto orderDto) {
        log.info("Iniciando persistência do pedido para o carrinho: {}", orderDto.getBasketId());
        Integer orderId = orderService.createOrder(orderDto);
        if (orderId != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
        }
        log.error("Erro fatal ao salvar pedido no Postgres.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Remove ou cancela um pedido pelo ID")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        log.info("Solicitação de exclusão para o pedido ID: {}", orderId);
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}