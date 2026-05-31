package com.ecommerce.sportscenter.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.sportscenter.dto.ProductResponse;

public interface ProductService {

    ProductResponse getProductById(
            @NotNull(message = "O ID do produto não pode ser nulo")
            @Positive(message = "O ID do produto deve ser um número positivo") Integer productId
    );

    Page<ProductResponse> getProducts(
            @NotNull(message = "Os parâmetros de paginação não podem ser nulos") Pageable pageable, 
            Integer brandId, 
            Integer typeId, 
            String keyword
    );
}