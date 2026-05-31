package com.ecommerce.sportscenter.dto;

import com.ecommerce.sportscenter.entity.OrderAggregate.OrderStatus;
import com.ecommerce.sportscenter.entity.OrderAggregate.ShippingAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de resposta contendo os detalhes consolidados de um pedido")
public class OrderResponse {

    @NotNull(message = "O ID do pedido é obrigatório na resposta")
    @Schema(description = "Identificador único do pedido gerado pelo banco de dados", example = "101")
    private Integer id;

    @NotBlank(message = "O ID do carrinho de origem é obrigatório")
    @Schema(description = "Identificador do carrinho que originou este pedido", example = "cliente@email.com")
    private String basketId;

    @NotNull(message = "O endereço de entrega não pode ser nulo")
    @Valid
    @Schema(description = "Endereço completo utilizado para o envio")
    private ShippingAddress shippingAddress;

    @NotNull(message = "O subtotal é obrigatório")
    @Min(value = 0, message = "O subtotal não pode ser negativo")
    @Schema(description = "Soma dos valores dos itens em centavos", example = "150000")
    private Long subTotal;

    @NotNull(message = "A taxa de entrega é obrigatória")
    @Min(value = 0, message = "A taxa de entrega não pode ser negativa")
    @Schema(description = "Valor cobrado pelo frete em centavos", example = "2500")
    private Long deliveryFee;

    @NotNull(message = "O valor total calculado é obrigatório")
    @Min(value = 0, message = "O valor total não pode ser negativo")
    @Schema(description = "Valor total consolidado (subtotal + frete) em centavos", example = "152500") // Mantido o padrão de centavos para consistência com as demais propriedades
    private Long total;

    @Schema(description = "Data e hora oficial do registro do pedido no sistema", example = "2026-05-31T09:05:00")
    private LocalDateTime orderDate;

    @NotNull(message = "O status atual do pedido é obrigatório")
    @Schema(description = "Estado do ciclo de vida em que o pedido se encontra no momento", example = "Pending")
    private OrderStatus orderStatus;
}