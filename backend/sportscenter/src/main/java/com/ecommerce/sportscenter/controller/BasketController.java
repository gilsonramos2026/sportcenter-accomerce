package com.ecommerce.sportscenter.controller;

import com.ecommerce.sportscenter.dto.BasketItemResponse;
import com.ecommerce.sportscenter.dto.BasketResponse;
import com.ecommerce.sportscenter.entity.Basket;
import com.ecommerce.sportscenter.entity.BasketItem;
import com.ecommerce.sportscenter.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baskets")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Basket", description = "Endpoints para gerenciamento do carrinho de compras em cache (Redis)")
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    @Operation(summary = "Busca todos os carrinhos ativos no cache")
    public ResponseEntity<List<BasketResponse>> getAllBaskets() {
        log.info("Buscando todos os carrinhos ativos no Redis.");
        List<BasketResponse> baskets = basketService.getAllBaskets();
        return ResponseEntity.ok(baskets);
    }

    @GetMapping("/{basketId}")
    @Operation(summary = "Busca um carrinho específico pelo ID temporário")
    public ResponseEntity<BasketResponse> getBasketById(@PathVariable String basketId) {
        log.info("Buscando carrinho com ID: {}", basketId);
        BasketResponse basketResponse = basketService.getBasketById(basketId);
        return ResponseEntity.ok(basketResponse);
    }

    @DeleteMapping("/{basketId}")
    @Operation(summary = "Remove um carrinho ativo do cache")
    public ResponseEntity<Void> deleteBasketById(@PathVariable String basketId) {
        log.info("Removendo carrinho ID: {}", basketId);
        basketService.deleteBasketById(basketId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Cria ou atualiza os itens de um carrinho")
    public ResponseEntity<BasketResponse> createBasket(@Valid @RequestBody BasketResponse basketResponse) {
        log.info("Salvando/Atualizando carrinho com ID: {}", basketResponse.getId());
        Basket basket = convertToBasketEntity(basketResponse);
        BasketResponse createdBasket = basketService.createBasket(basket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBasket);
    }

    private Basket convertToBasketEntity(BasketResponse basketResponse) {
        if (basketResponse == null) return null;
        Basket basket = new Basket();
        basket.setId(basketResponse.getId());
        basket.setItems(mapBasketItemResponsesToEntities(basketResponse.getItems()));
        return basket;
    }

    private List<BasketItem> mapBasketItemResponsesToEntities(List<BasketItemResponse> itemResponses) {
        if (itemResponses == null) return List.of();
        return itemResponses.stream()
                .map(this::convertToBasketItemEntity)
                .toList(); // Java 21 Otimizado
    }

    private BasketItem convertToBasketItemEntity(BasketItemResponse itemResponse) {
        if (itemResponse == null) return null;
        BasketItem basketItem = new BasketItem();
        basketItem.setId(itemResponse.getId());
        basketItem.setName(itemResponse.getName());
        basketItem.setDescription(itemResponse.getDescription());
        basketItem.setPrice(itemResponse.getPrice());
        basketItem.setPictureUrl(itemResponse.getPictureUrl());
        basketItem.setProductBrand(itemResponse.getProductBrand());
        basketItem.setProductType(itemResponse.getProductType());
        basketItem.setQuantity(itemResponse.getQuantity());
        return basketItem;
    }
}