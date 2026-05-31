package com.ecommerce.sportscenter.controller;

import com.ecommerce.sportscenter.dto.BasketItemResponse;
import com.ecommerce.sportscenter.dto.BasketResponse;
import com.ecommerce.sportscenter.entity.Basket;
import com.ecommerce.sportscenter.entity.BasketItem;
import com.ecommerce.sportscenter.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baskets")
@Log4j2
@RequiredArgsConstructor // Injeção automática via construtor do Lombok para o service final
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    public ResponseEntity<List<BasketResponse>> getAllBaskets() {
        log.info("Fetching all active baskets from cache.");
        List<BasketResponse> baskets = basketService.getAllBaskets();
        return ResponseEntity.ok(baskets);
    }

    @GetMapping("/{basketId}")
    public ResponseEntity<BasketResponse> getBasketById(@PathVariable String basketId) {
        log.info("Fetching basket with ID: {}", basketId);
        BasketResponse basketResponse = basketService.getBasketById(basketId);
        return ResponseEntity.ok(basketResponse);
    }

    @DeleteMapping("/{basketId}")
    public ResponseEntity<Void> deleteBasketById(@PathVariable String basketId) {
        log.info("Deleting basket with ID: {}", basketId);
        basketService.deleteBasketById(basketId);
        return ResponseEntity.noContent().build(); // Retorna o status HTTP 204 No Content (padrão ideal para remoções)
    }

    @PostMapping
    public ResponseEntity<BasketResponse> createBasket(@RequestBody BasketResponse basketResponse) {
        log.info("Creating/Updating basket with ID: {}", basketResponse.getId());
        
        // Converte o objeto de transferência de resposta recebido para a entidade interna do domínio
        Basket basket = convertToBasketEntity(basketResponse);
        
        // Invoca a persistência rápida no servidor Redis
        BasketResponse createdBasket = basketService.createBasket(basket);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBasket);
    }

    // =========================================================================
    // MÉTODOS DE MAPEAMENTO E CONVERSÃO (Otimizados com Java 21 Streams)
    // =========================================================================

    private Basket convertToBasketEntity(BasketResponse basketResponse) {
        if (basketResponse == null) {
            return null;
        }
        Basket basket = new Basket();
        basket.setId(basketResponse.getId());
        basket.setItems(mapBasketItemResponsesToEntities(basketResponse.getItems()));
        return basket;
    }

    private List<BasketItem> mapBasketItemResponsesToEntities(List<BasketItemResponse> itemResponses) {
        if (itemResponses == null) {
            return List.of();
        }
        // Substituído pelo .toList() nativo do Java 21 para otimização de alocação de memória
        return itemResponses.stream()
                .map(this::convertToBasketItemEntity)
                .toList();
    }

    private BasketItem convertToBasketItemEntity(BasketItemResponse itemResponse) {
        if (itemResponse == null) {
            return null;
        }
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
