package com.ecommerce.sportscenter.repository;

import com.ecommerce.sportscenter.entity.Type;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Validated
public interface TypeRepository extends JpaRepository<Type, Integer> {

    @Override
    @NotNull(message = "O ID de busca da categoria/tipo não pode ser nulo")
    Optional<Type> findById(Integer id);
}
