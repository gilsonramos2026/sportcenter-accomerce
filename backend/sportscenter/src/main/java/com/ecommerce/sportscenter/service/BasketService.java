package com.ecommerce.sportscenter.service;

import com.ecommerce.sportscenter.dto.BasketResponse;
import com.ecommerce.sportscenter.entity.Basket;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface BasketService {
    
    List<BasketResponse> getAllBaskets();
    
    BasketResponse getBasketById(@NotBlank(message = "O ID do carrinho não pode ser nulo ou vazio") String basketId);
    
    void deleteBasketById(@NotBlank(message = "O ID do carrinho não pode ser nulo ou vazio") String basketId);
    
    BasketResponse createBasket(@NotNull(message = "Os dados do carrinho não podem ser nulos") @Valid Basket basket);
}