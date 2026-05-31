package com.ecommerce.sportscenter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferência de dados que representa os detalhes públicos de um produto do catálogo")
public class ProductResponse {

    @NotNull(message = "O ID do produto é obrigatório")
    @Schema(description = "ID único do produto gerado pelo banco de dados", example = "1")
    private Integer id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Schema(description = "Nome comercial do produto", example = "Chuteira Nike Phantom GX")
    private String name;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Schema(description = "Descrição detalhada sobre as características do produto", example = "Chuteira de alta precisão com travas para campo firme.")
    private String description;

    @NotNull(message = "O preço do produto é obrigatório")
    @Min(value = 0, message = "O preço do produto não pode ser um valor negativo")
    @Schema(description = "Preço unitário do produto em centavos", example = "49990") // 49990 = R$ 499,90
    private Long price;

    @NotBlank(message = "A URL da imagem é obrigatória")
    @Schema(description = "Caminho relativo da imagem do produto no servidor de arquivos", example = "images/products/nike-phantom.png")
    private String pictureUrl;

    @NotBlank(message = "A marca do produto é obrigatória")
    @Schema(description = "Nome textual da marca associada ao produto", example = "Nike")
    private String productBrand;

    @NotBlank(message = "O tipo de produto é obrigatório")
    @Schema(description = "Nome textual da categoria/tipo do produto", example = "Calçados")
    private String productType;
}
