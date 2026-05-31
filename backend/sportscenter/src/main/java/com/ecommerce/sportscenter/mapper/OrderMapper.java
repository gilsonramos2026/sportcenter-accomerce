package com.ecommerce.sportscenter.mapper;

import com.ecommerce.sportscenter.dto.OrderDto;
import com.ecommerce.sportscenter.dto.OrderResponse;
import com.ecommerce.sportscenter.entity.OrderAggregate.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "basketId", target = "basketId")
    @Mapping(source = "shippingAddress", target = "shippingAddress")
    @Mapping(source = "subTotal", target = "subTotal") // Usará o método mapDoubleToLong automaticamente
    @Mapping(source = "deliveryFee", target = "deliveryFee") // Usará o método mapDoubleToLong automaticamente
    @Mapping(target = "total", expression = "java(calculateTotal(order))")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "orderStatus", constant = "Pending")
    OrderResponse OrderToOrderResponse(Order order);

    @Mapping(target = "orderDate", expression = "java(orderDto.getOrderDate())")
    @Mapping(target = "orderStatus", constant = "Pending") 
    @Mapping(target = "id", ignore = true)
    Order orderResponseToOrder(OrderDto orderDto);

    List<OrderDto> ordersToOrderResponses(List<Order> orders);

    void updateOrderFromOrderResponse(OrderDto orderDto, @MappingTarget Order order);

    // =========================================================================
    // MÉTODOS AUXILIARES DE CONVERSÃO (Tratam a divergência de double/Long)
    // =========================================================================

    /**
     * Calcula o total somando as propriedades double da entidade e convertendo de forma segura para Long.
     */
    default Long calculateTotal(Order order) {
        if (order == null) {
            return 0L;
        }
        double total = order.getSubTotal() + order.getDeliveryFee();
        return Double.valueOf(total).longValue();
    }

    /**
     * Método padrão que o MapStruct usará automaticamente sempre que precisar 
     * mapear um tipo primitivo 'double' para um objeto wrapper 'Long' neste Mapper.
     */
    default Long mapDoubleToLong(double value) {
        return Double.valueOf(value).longValue();
    }
}