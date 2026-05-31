package com.ecommerce.sportscenter.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.sportscenter.dto.OrderDto;
import com.ecommerce.sportscenter.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse getOrderById(
            @NotNull(message = "O ID do pedido não pode ser nulo") 
            @Positive(message = "O ID do pedido deve ser um número positivo") Integer orderId
    );

    List<OrderResponse> getAllOrders();

    Page<OrderResponse> getAllOrders(
            @NotNull(message = "Os parâmetros de paginação não podem ser nulos") Pageable pageable
    );

    // Retorna o ID do pedido criado (Integer) baseado no DTO de entrada validado em cascata (@Valid)
    Integer createOrder(
            @NotNull(message = "Os dados do pedido não podem ser nulos") 
            @Valid OrderDto order
    );

    void deleteOrder(
            @NotNull(message = "O ID do pedido para exclusão não pode ser nulo") 
            @Positive(message = "O ID do pedido para exclusão deve ser um número positivo") Integer orderId
    );
}