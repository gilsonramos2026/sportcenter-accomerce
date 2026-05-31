package com.ecommerce.sportscenter.repository;

import com.ecommerce.sportscenter.entity.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Validated
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Override
    @NotNull(message = "O ID de busca do produto não pode ser nulo")
    Optional<Product> findById(Integer id);
}