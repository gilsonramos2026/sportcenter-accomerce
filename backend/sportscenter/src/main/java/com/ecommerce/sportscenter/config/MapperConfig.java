package com.ecommerce.sportscenter.config;

import com.ecommerce.sportscenter.mapper.OrderMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public OrderMapper orderMapper() {
        // Retorna a instância gerenciada do MapStruct de forma limpa
        return OrderMapper.INSTANCE;
    }
}