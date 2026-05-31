package com.ecommerce.sportscenter.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor // Injeção automática via construtor do Lombok para os campos final
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String requestHeader = request.getHeader("Authorization");
        String userName = null;
        String token = null;

        // Correção de segurança: Adicionado espaço após o Bearer para evitar quebras de string
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            try {
                userName = this.jwtHelper.getUserNameFromToken(token);
            } catch (IllegalArgumentException e) {
                log.error("Argumento ilegal ao processar as Claims do Token JWT");
            } catch (ExpiredJwtException e) {
                log.warn("O Token JWT fornecido está expirado");
            } catch (MalformedJwtException e) {
                log.error("O Token JWT fornecido está malformado ou corrompido");
            } catch (SignatureException e) {
                log.error("A assinatura do Token JWT é inválida");
            }
        } else {
            // Log alterado para debug/trace para não poluir o console em rotas públicas abertas pelo Swagger/Postgres
            log.trace("Cabeçalho de autorização ausente ou não inicia com Bearer String");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Busca os detalhes do usuário na base de dados
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if (Boolean.TRUE.equals(validateToken)) {
                // Configura o objeto de autenticação dentro do contexto do Spring Security
                UsernamePasswordAuthenticationToken authenticationToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.debug("Usuário {} autenticado com sucesso via JWT para a requisição", userName);
            } else {
                log.warn("Token JWT falhou na validação para o usuário: {}", userName);
            }
        }

        filterChain.doFilter(request, response);
    }
}
