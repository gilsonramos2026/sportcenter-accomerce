package com.ecommerce.sportscenter.security;

import com.ecommerce.sportscenter.entity.Basket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de Infraestrutura para Gerenciamento do Carrinho de Compras.
 * * Esta interface utiliza o Spring Data Redis (através da extensão de CrudRepository)
 * para persistir e buscar os dados de sessão do carrinho em memória RAM de alto desempenho.
 * O ID do carrinho é mapeado como uma String (geralmente um UUID gerado pelo cliente/frontend).
 */
@Repository
public interface BasketRepository extends CrudRepository<Basket, String> {
    // Métodos padrão herdados do CrudRepository como:
    // - save(Basket basket)
    // - findById(String id)
    // - deleteById(String id)
    // - existsById(String id)
}