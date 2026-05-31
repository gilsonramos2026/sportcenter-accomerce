package com.ecommerce.sportscenter.controller;

import com.ecommerce.sportscenter.dto.JwtResponse;
import com.ecommerce.sportscenter.security.JwtHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Log4j2
@RequiredArgsConstructor // Injeção automática via construtor para todas as dependências finais
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager manager;
    private final JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest request) {
        log.info("Tentativa de login iniciada para o usuário: {}", request.getUsername());
        
        // Autentica as credenciais contra a base do Postgres através do AuthenticationManager
        this.authenticate(request.getUsername(), request.getPassword());
        
        // Carrega os detalhes do usuário e gera o Token JWT seguro (JJWT 0.12.5)
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = this.jwtHelper.generateToken(userDetails);
        
        JwtResponse response = JwtResponse.builder()
                .username(userDetails.getUsername())
                .token(token)
                .build();
                
        log.info("Usuário {} autenticado com sucesso. Token gerado.", request.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDetails> getUserDetails(@RequestHeader("Authorization") String tokenHeader) {
        final String token = extractTokenFromHeader(tokenHeader);
        
        if (token != null) {
            String username = jwtHelper.getUserNameFromToken(token);
            log.debug("Buscando detalhes cadastrais para o usuário extraído do JWT: {}", username);
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return ResponseEntity.ok(userDetails);
        }
        
        log.warn("Tentativa falha de ler dados do usuário: Cabeçalho 'Authorization' malformado ou ausente.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private String extractTokenFromHeader(String tokenHeader) {
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7); // Remove o prefixo "Bearer " com segurança
        }
        return null;
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException ex) {
            log.warn("Falha de autenticação: Credenciais inválidas para o usuário {}", username);
            // Lança a exceção que será capturada pelo JwtAuthenticationEntryPoint ou por um RestControllerAdvice
            throw new BadCredentialsException("Usuário ou Senha inválidos.");
        }
    }
}
