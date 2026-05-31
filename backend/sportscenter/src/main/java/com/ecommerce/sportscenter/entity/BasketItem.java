package com.ecommerce.sportscenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_basket_item")
@Schema(description = "Representa um item específico dentro do carrinho de compras")
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do item gerado automaticamente pelo banco", example = "1")
    private Integer id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Schema(description = "Nome do produto esportivo", example = "Chuteira Nike Mercurial")
    private String name;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Schema(description = "Descrição detalhada do produto", example = "Chuteira de campo para alta performance")
    private String description;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "O preço não pode ser negativo")
    @Schema(description = "Preço unitário em centavos ou valor inteiro longo", example = "29990")
    private Long price;

    @NotBlank(message = "A URL da imagem é obrigatória")
    @Schema(description = "Link para a imagem do produto", example = "images/products/nike-merc.png")
    private String pictureUrl;

    @NotBlank(message = "A marca do produto é obrigatória")
    @Schema(description = "Marca fabricante do produto", example = "Nike")
    private String productBrand;

    @NotBlank(message = "O tipo do produto é obrigatório")
    @Schema(description = "Categoria do produto no catálogo", example = "Calçados")
    private String productType;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade mínima para adicionar ao carrinho é 1")
    @Schema(description = "Quantidade total deste item adicionada", example = "2")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    @JsonIgnore // Evita loops de recursão infinita ao converter para JSON
    private Basket basket;
}