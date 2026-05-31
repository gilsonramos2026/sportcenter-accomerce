package com.ecommerce.sportscenter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Objeto de transferência de dados que representa uma categoria/tipo de produto")
public class TypeResponse {

    @NotNull(message = "O ID do tipo é obrigatório")
    @Schema(description = "ID único do tipo/categoria gerado pelo banco", example = "1")
    private Integer id;

    @NotBlank(message = "O nome do tipo é obrigatório")
    @Schema(description = "Nome descritivo da categoria", example = "Calçados")
    private String name;
}
