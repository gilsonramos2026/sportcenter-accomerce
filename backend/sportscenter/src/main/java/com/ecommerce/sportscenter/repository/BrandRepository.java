package com.ecommerce.sportscenter.repository;

import com.ecommerce.sportscenter.entity.Brand;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Validated // Ativa a validação de parâmetros em tempo de execução
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Override
    @NotNull(message = "O ID de busca da marca não pode ser nulo")
    Optional<Brand> findById(Integer id);
}
