package com.ecommerce.sportscenter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecommerce.sportscenter.dto.CustomErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        
        // Construindo a resposta estruturada utilizando o padrão Builder atualizado
        CustomErrorResponse customErrorResponse = CustomErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value()) // Retorna 404
                .error("Product doesn't exist")
                .message(ex.getMessage())
                // O timestamp já é preenchido automaticamente via @Builder.Default
                .build();

        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }
}
