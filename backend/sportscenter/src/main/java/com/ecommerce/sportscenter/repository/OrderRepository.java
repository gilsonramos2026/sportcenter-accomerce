package com.ecommerce.sportscenter.repository;

import com.ecommerce.sportscenter.entity.OrderAggregate.Order;
import com.ecommerce.sportscenter.entity.OrderAggregate.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Validated
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByBasketId(@NotBlank(message = "O ID do carrinho não pode ser nulo ou vazio") String basketId);

    List<Order> findByOrderStatus(@NotNull(message = "O status do pedido não pode ser nulo") OrderStatus orderStatus);

    List<Order> findByOrderDateBetween(
            @NotNull(message = "A data inicial não pode ser nula") LocalDateTime startdate, 
            @NotNull(message = "A data final não pode ser nula") LocalDateTime endDate
    );

    @Query("SELECT o FROM Order o JOIN o.orderItems oi WHERE oi.itemOrdered.name LIKE %:productName%")
    List<Order> findByProductNameInOrderItems(
            @NotBlank(message = "O nome do produto para pesquisa não pode ser nulo ou vazio") @Param("productName") String productName
    );

    @Query("SELECT o FROM Order o WHERE o.shippingAddress.city = :city")
    List<Order> findByShippingAddressCity(
            @NotBlank(message = "A cidade para pesquisa não pode ser nula ou vazia") @Param("city") String city
    );
}