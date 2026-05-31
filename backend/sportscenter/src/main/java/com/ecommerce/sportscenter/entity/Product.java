package com.ecommerce.sportscenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representa um produto esportivo disponível no catálogo")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "ID único do produto gerado pelo banco", example = "1")
    private Integer id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    @Schema(description = "Nome comercial do produto", example = "Chuteira Nike Phantom GX")
    private String name;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Size(max = 500, message = "A descrição não pode passar de 500 caracteres")
    @Column(name = "description", length = 500)
    @Schema(description = "Detalhes e especificações técnicas do produto", example = "Chuteira de alta precisão com travas para campo firme.")
    private String description;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "O preço não pode ser negativo")
    @Column(name = "price", nullable = false)
    @Schema(description = "Preço unitário do produto em formato inteiro (centavos)", example = "49990")
    private Long price;

    @NotBlank(message = "A URL da imagem é obrigatória")
    @Column(name = "picture_url", nullable = false)
    @Schema(description = "Caminho ou link da imagem do produto", example = "images/products/nike-phantom.png")
    private String pictureUrl;

    @NotNull(message = "A marca do produto é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_brand_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "products"})
    @Schema(description = "Marca do produto")
    private Brand brand;

    @NotNull(message = "O tipo do produto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "products"})
    @Schema(description = "Categoria/Tipo do produto")
    private Type type;
}