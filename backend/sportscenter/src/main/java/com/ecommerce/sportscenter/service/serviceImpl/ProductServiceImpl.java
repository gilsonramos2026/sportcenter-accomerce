package com.ecommerce.sportscenter.service.serviceImpl;

import com.ecommerce.sportscenter.dto.ProductResponse;
import com.ecommerce.sportscenter.entity.Product;
import com.ecommerce.sportscenter.exception.ProductNotFoundException;
import com.ecommerce.sportscenter.repository.ProductRepository;
import com.ecommerce.sportscenter.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Log4j2
@Validated
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Integer productId) {
        log.info("Fetching Product by Id: {}", productId);
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " doesn't exist"));
        
        ProductResponse productResponse = convertToProductResponse(product);
        log.info("Fetched Product successfully by Product Id: {}", productId);
        return productResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProducts(Pageable pageable, Integer brandId, Integer typeId, String keyword) {
        log.info("Searching products with criteria - BrandId: {}, TypeId: {}, Keyword: {}", brandId, typeId, keyword);
        
        // CORREÇÃO: Fornece explicitamente o tipo genérico ao operador 'where' eliminando a ambiguidade do null
        Specification<Product> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (brandId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                    criteriaBuilder.equal(root.get("brand").get("id"), brandId));
        }

        if (typeId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                    criteriaBuilder.equal(root.get("type").get("id"), typeId));
        }

        if (keyword != null && !keyword.strip().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase().trim() + "%"));
        }

        return productRepository.findAll(spec, pageable)
                .map(this::convertToProductResponse);
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .pictureUrl(product.getPictureUrl())
                .productBrand(product.getBrand() != null ? product.getBrand().getName() : null)
                .productType(product.getType() != null ? product.getType().getName() : null)
                .build();
    }
}