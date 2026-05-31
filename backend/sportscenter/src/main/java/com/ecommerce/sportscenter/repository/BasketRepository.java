package com.ecommerce.sportscenter.repository;

import com.ecommerce.sportscenter.entity.Basket;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Validated // Ativa a validação de argumentos em tempo de execução para os métodos da interface
public interface BasketRepository extends CrudRepository<Basket, String> {

    @Override
    @NotNull(message = "O ID de busca do carrinho não pode ser nulo")
    Optional<Basket> findById(String id);

    @Override
    void deleteById(@NotBlank(message = "O ID para exclusão do carrinho não pode ser nulo ou vazio") String id);
}
