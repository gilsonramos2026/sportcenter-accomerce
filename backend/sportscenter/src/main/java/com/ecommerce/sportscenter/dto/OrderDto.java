package com.ecommerce.sportscenter.dto;

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
@Schema(description = "Objeto de requisição enviado pelo cliente para a criação de um novo pedido")
public class OrderDto {

    @NotBlank(message = "O ID do carrinho de origem (basketId) é obrigatório")
    @Schema(description = "Identificador exclusivo do carrinho que será transformado em pedido", example = "cliente@email.com")
    private String basketId;

    @NotNull(message = "Os dados de endereço de entrega são obrigatórios")
    @Valid // Aciona as regras de validação internas da classe ShippingAddress
    @Schema(description = "Dados do endereço de envio para a entrega do pedido")
    private ShippingAddress shippingAddress;

    @NotNull(message = "O subtotal do pedido é obrigatório")
    @Min(value = 0, message = "O subtotal do pedido não pode ser negativo")
    @Schema(description = "Valor total dos itens somados em centavos", example = "150000") // R$ 1.500,00
    private Long subTotal;

    @NotNull(message = "A taxa de entrega é obrigatória")
    @Min(value = 0, message = "A taxa de entrega não pode ser valor negativo")
    @Schema(description = "Valor do frete calculado para o endereço informado, em centavos", example = "2500") // R$ 25,00
    private Long deliveryFee;

    @Builder.Default
    @Schema(description = "Data e hora em que a intenção de pedido foi gerada", example = "2026-05-31T09:00:00")
    private LocalDateTime orderDate = LocalDateTime.now();
}