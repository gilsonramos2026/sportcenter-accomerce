package com.ecommerce.sportscenter.entity.OrderAggregate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representa o histórico/instantâneo do produto no momento exato da compra")
public class ProductItemOrdered {

    @NotNull(message = "O ID do produto original é obrigatório")
    @Column(name = "product_id", nullable = false)
    @Schema(description = "ID do produto vindo do catálogo principal", example = "1")
    private Integer productId;

    @NotBlank(message = "O nome do produto no momento da compra é obrigatório")
    @Column(name = "product_name", nullable = false)
    @Schema(description = "Nome do produto gravado no histórico do pedido", example = "Chuteira Nike Phantom GX")
    private String name;

    @NotBlank(message = "A URL da imagem do produto é obrigatória")
    @Column(name = "product_picture_url", nullable = false)
    @Schema(description = "Link ou caminho da imagem do produto gravado no histórico", example = "images/products/nike-phantom.png")
    private String pictureUrl;
}