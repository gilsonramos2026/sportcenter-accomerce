package com.ecommerce.sportscenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class MyConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Definição de usuário em memória utilizando o padrão seguro de encode de senhas
        UserDetails userDetails = User.builder()
                .username("rahul")
                .password(passwordEncoder().encode("Password"))
                .roles("ADMIN") // Roles por convenção em letras maiúsculas
                .build();
                
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
