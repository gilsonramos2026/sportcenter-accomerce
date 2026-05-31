package com.ecommerce.sportscenter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Objeto de requisição para autenticação de usuários (Credenciais)")
public class JwtRequest {

    @NotBlank(message = "O nome de usuário ou e-mail é obrigatório")
    @Size(min = 3, max = 50, message = "O usuário deve ter entre 3 e 50 caracteres")
    @Schema(description = "Nome de usuário ou e-mail cadastrado no sistema", example = "gilson_ramos")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres")
    @Schema(description = "Senha secreta do usuário", example = "Senha@123")
    private String password;
}
