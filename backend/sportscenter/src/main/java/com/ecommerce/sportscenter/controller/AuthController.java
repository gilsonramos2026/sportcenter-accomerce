package com.ecommerce.sportscenter.controller;

import com.ecommerce.sportscenter.dto.JwtRequest;
import com.ecommerce.sportscenter.dto.JwtResponse;
import com.ecommerce.sportscenter.security.JwtHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints para gerenciamento de login e sessão de segurança")
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager manager;
    private final JwtHelper jwtHelper;

    @PostMapping("/login")
    @Operation(summary = "Autentica credenciais e emite token de acesso JWT")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest request) {
        log.info("Tentativa de autenticação iniciada para o login: {}", request.getUsername());
        this.authenticate(request.getUsername(), request.getPassword());
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);
        
        JwtResponse response = JwtResponse.builder()
                .username(userDetails.getUsername())
                .token(token)
                .build();
                
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user")
    @Operation(summary = "Retorna os detalhes completos do usuário a partir da leitura do Token ativo")
    public ResponseEntity<UserDetails> getUserDetails(@RequestHeader("Authorization") String tokenHeader) {
        String token = extractTokenFromHeader(tokenHeader);
        
        if (token != null) {
            String username = jwtHelper.getUserNameFromToken(token);
            log.info("Carregando contexto de segurança para o usuário: {}", username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } else {
            log.warn("Tentativa falha de leitura cadastral: Cabeçalho Authorization inválido.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String extractTokenFromHeader(String tokenHeader) {
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7);
        }
        return null;
    }

    private void authenticate(String username, String password) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException ex) {
            log.warn("Falha no login: Senha ou usuário incorreto para {}", username);
            throw new BadCredentialsException("Invalid UserName or Password");
        }
    }
}