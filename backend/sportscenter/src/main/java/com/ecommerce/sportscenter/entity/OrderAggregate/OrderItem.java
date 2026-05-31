package com.ecommerce.sportscenter.entity.OrderAggregate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representa um item individual que foi consolidado dentro de um pedido")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "ID único do item do pedido gerado pelo banco", example = "1")
    private Integer id;

    @Valid
    @NotNull(message = "Os detalhes do produto comprado são obrigatórios")
    @Embedded
    @Schema(description = "Instantâneo (Snapshot) do produto no momento da compra")
    private ProductItemOrdered itemOrdered;

    @NotNull(message = "O preço do item é obrigatório")
    @Min(value = 0, message = "O preço do item não pode ser negativo")
    @Column(name = "price", nullable = false)
    @Schema(description = "Preço unitário do produto no momento da compra (em centavos)", example = "29990")
    private Long price;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade mínima de um item no pedido deve ser 1")
    @Column(name = "quantity", nullable = false)
    @Schema(description = "Quantidade total comprada deste item", example = "2")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore // Evita loops de recursão infinita na resposta da API
    @Schema(description = "Pedido ao qual este item pertence")
    private Order order;
}