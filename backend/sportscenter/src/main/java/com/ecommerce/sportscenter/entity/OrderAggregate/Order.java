package com.ecommerce.sportscenter.entity.OrderAggregate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representa o pedido consolidado feito pelo cliente (Order Root Aggregate)")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "ID único do pedido gerado pelo banco", example = "1050")
    private Integer id;

    @NotBlank(message = "O ID do carrinho de origem é obrigatório")
    @Column(name = "basket_id", nullable = false)
    @Schema(description = "ID do carrinho que gerou este pedido", example = "cliente@email.com")
    private String basketId;

    @Valid
    @NotNull(message = "O endereço de entrega é obrigatório")
    @Embedded
    @Schema(description = "Endereço de entrega embutido no pedido")
    private ShippingAddress shippingAddress;

    @NotNull(message = "A data do pedido é obrigatória")
    @Builder.Default
    @Column(name = "order_date", nullable = false)
    @Schema(description = "Data e hora em que o pedido foi efetuado", example = "2026-05-31T10:00:00")
    private LocalDateTime orderDate = LocalDateTime.now();

    @Valid
    @NotEmpty(message = "O pedido deve conter pelo menos um item")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    @Schema(description = "Lista de itens que compõem o pedido")
    private List<OrderItem> orderItems;

    @NotNull(message = "O subtotal é obrigatório")
    @PositiveOrZero(message = "O subtotal não pode ser negativo")
    @Column(name = "sub_total", nullable = false)
    @Schema(description = "Soma dos valores dos itens (sem o frete)", example = "599.80")
    private Double subTotal;

    @NotNull(message = "A taxa de entrega é obrigatória")
    @PositiveOrZero(message = "A taxa de entrega não pode ser negativa")
    @Column(name = "delivery_fee", nullable = false)
    @Schema(description = "Valor do frete cobrado", example = "2000") // Se for centavos, ex: 2000 = R$ 20.00
    private Long deliveryFee;

    @NotNull(message = "O status do pedido é obrigatório")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "order_status", nullable = false)
    @Schema(description = "Status atual do fluxo do pedido", example = "Pending")
    private OrderStatus orderStatus = OrderStatus.Pending;

    @Schema(description = "Calcula o valor total do pedido (Subtotal + Taxa de Entrega)", example = "619.80")
    public Double getTotal() {
        double sub = this.subTotal != null ? this.subTotal : 0.0;
        double fee = this.deliveryFee != null ? this.deliveryFee.doubleValue() : 0.0;
        
        // Se o seu deliveryFee estiver em centavos (ex: 2000 para R$20.00) e o subtotal em Double normal (599.80),
        // descomente a linha abaixo para ajustar a escala antes de somar:
        // fee = fee / 100.0;

        return sub + fee;
    }
}
