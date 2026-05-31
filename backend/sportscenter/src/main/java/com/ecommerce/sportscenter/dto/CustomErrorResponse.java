package com.ecommerce.sportscenter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Estrutura padrão de resposta para erros e exceções da API")
public class CustomErrorResponse {

    @Schema(description = "Código de status HTTP do erro", example = "BAD_REQUEST")
    private HttpStatus status;

    @Schema(description = "Código numérico do status HTTP correspondente", example = "400")
    private Integer statusCode;

    @Schema(description = "Título ou categoria simplificada do erro", example = "Bad Request")
    private String error;

    @Schema(description = "Mensagem detalhada descrevendo o motivo do erro", example = "O ID do carrinho de origem é obrigatório")
    private String message;

    @Builder.Default
    @Schema(description = "Data e hora exata em que o erro ocorreu", example = "2026-05-31T10:15:30")
    private LocalDateTime timestamp = LocalDateTime.now();
}