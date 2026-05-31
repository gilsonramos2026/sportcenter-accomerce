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
@Schema(description = "Objeto de transferência de dados que representa uma marca de produto")
public class BrandResponse {

    @NotNull(message = "O ID da marca é obrigatório")
    @Schema(description = "ID único da marca gerado pelo banco", example = "1")
    private Integer id;

    @NotBlank(message = "O nome da marca é obrigatório")
    @Schema(description = "Nome comercial da marca", example = "Nike")
    private String name;
}