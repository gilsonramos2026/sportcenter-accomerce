package com.ecommerce.sportscenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// REMOVIDO: @EnableWebMvc foi retirado para não desconfigurar os mappers do Jackson e o comportamento do Pageable
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Permite chamadas locais do seu frontend (Vite/React na porta 5173 ou similar)
                .allowedOriginPatterns("*") 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                // Permite que o Axios envie e leia os cabeçalhos de Autorização (Bearer Token) perfeitamente
                .allowCredentials(true)
                .maxAge(3600); // Cache do pre-flight request por 1 hora
    }
}
