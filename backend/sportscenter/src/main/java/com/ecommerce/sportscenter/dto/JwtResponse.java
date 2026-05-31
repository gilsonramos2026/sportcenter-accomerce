package com.ecommerce.sportscenter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Objeto de resposta retornado após autenticação bem-sucedida contendo o token de acesso")
public class JwtResponse {

    @NotBlank(message = "O nome de usuário não pode ser nulo na resposta")
    @Schema(description = "Nome do usuário que foi autenticado", example = "gilson_ramos")
    private String username;

    @NotBlank(message = "O token JWT não pode ser nulo na resposta")
    @Schema(description = "Token JWT gerado pelo servidor para autorizar requisições subsequentes", 
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...[token_completo]")
    private String token;
}
