package com.ecommerce.sportscenter.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor // Injeta o ObjectMapper do Spring automaticamente via construtor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, 
                         HttpServletResponse response, 
                         AuthenticationException authException) throws IOException, ServletException {
        
        log.error("Resposta não autorizada (Unauthorized) capturada pelo EntryPoint no endpoint: {}. Mensagem: {}", 
                request.getRequestURI(), authException.getMessage());

        // Define o status HTTP 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Define o Content-Type como JSON para compatibilidade exata com o ecossistema Swagger/Front-End
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // Cria uma estrutura de resposta rica e idêntica aos padrões de validação do Spring Boot
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", "Acesso Negado: " + authException.getMessage());
        body.put("path", request.getRequestURI());

        // Escreve o JSON diretamente no stream de saída usando o Jackson
        objectMapper.writeValue(response.getWriter(), body);
    }
}