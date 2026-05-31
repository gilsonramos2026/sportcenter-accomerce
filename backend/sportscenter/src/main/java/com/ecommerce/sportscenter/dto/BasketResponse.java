package com.ecommerce.sportscenter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferência de dados que representa o carrinho de compras completo")
public class BasketResponse {

    @NotBlank(message = "O ID do carrinho é obrigatório")
    @Schema(description = "Identificador único do carrinho (geralmente o e-mail ou UUID do cliente)", example = "cliente@email.com")
    private String id;

    @NotNull(message = "A lista de itens não pode ser nula")
    @Valid // Garante a validação em cascata de cada item dentro da lista
    @Schema(description = "Lista de itens adicionados ao carrinho")
    private List<BasketItemResponse> items;
}