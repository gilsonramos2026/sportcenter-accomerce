package com.ecommerce.sportscenter.service.serviceImpl;

import com.ecommerce.sportscenter.dto.BasketItemResponse;
import com.ecommerce.sportscenter.dto.BasketResponse;
import com.ecommerce.sportscenter.entity.Basket;
import com.ecommerce.sportscenter.entity.BasketItem;
import com.ecommerce.sportscenter.repository.BasketRepository;
import com.ecommerce.sportscenter.service.BasketService;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Validated // Ativa a validação dos parâmetros anotados na interface
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    public BasketServiceImpl(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public List<BasketResponse> getAllBaskets() {
        log.info("Fetching All Baskets");
        List<Basket> basketList = (List<Basket>) basketRepository.findAll();
        
        // Java 21 modernizado: Substituído .collect(Collectors.toList()) por .toList()
        List<BasketResponse> basketResponses = basketList.stream()
                .map(this::convertToBasketResponse)
                .toList();
                
        log.info("Fetched all Baskets");
        return basketResponses;
    }

    @Override
    public BasketResponse getBasketById(String basketId) {
        log.info("Fetching Basket by Id: {}", basketId);
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        
        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            log.info("Fetched Basket by Id: {}", basketId);
            return convertToBasketResponse(basket);
        } else {
            log.info("Basket with Id: {} not found", basketId);
            return null;
        }
    }

    @Override
    public void deleteBasketById(String basketId) {
        log.info("Deleting Basket by Id: {}", basketId);
        basketRepository.deleteById(basketId);
        log.info("Deleted Basket by Id: {}", basketId);
    }

    @Override
    public BasketResponse createBasket(Basket basket) {
        log.info("Creating Basket");
        Basket savedBasket = basketRepository.save(basket);
        log.info("Basket created with Id: {}", savedBasket.getId());
        return convertToBasketResponse(savedBasket);
    }

    private BasketResponse convertToBasketResponse(Basket basket) {
        if (basket == null) {
            return null;
        }
        
        // Java 21 modernizado: Usando .toList()
        List<BasketItemResponse> itemResponses = basket.getItems().stream()
                .map(this::convertToBasketItemResponse)
                .toList();
                
        return BasketResponse.builder()
                .id(basket.getId())
                .items(itemResponses)
                .build();
    }

    private BasketItemResponse convertToBasketItemResponse(BasketItem basketItem) {
        return BasketItemResponse.builder()
                .id(basketItem.getId())
                .name(basketItem.getName())
                .description(basketItem.getDescription())
                .price(basketItem.getPrice()) // Mantém precisão em centavos (Long)
                .pictureUrl(basketItem.getPictureUrl())
                .productBrand(basketItem.getProductBrand())
                .productType(basketItem.getProductType())
                .quantity(basketItem.getQuantity())
                .build();
    }
}