package com.ecommerce.sportscenter.controller;

import com.ecommerce.sportscenter.dto.BrandResponse;
import com.ecommerce.sportscenter.dto.ProductResponse;
import com.ecommerce.sportscenter.dto.TypeResponse;
import com.ecommerce.sportscenter.service.BrandService;
import com.ecommerce.sportscenter.service.ProductService;
import com.ecommerce.sportscenter.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Products Catalog", description = "Endpoints públicos da vitrine de produtos, marcas e tipos")
public class ProductController {

    private final ProductService productService;
    private final BrandService brandService;
    private final TypeService typeService;

    @GetMapping("/{id}")
    @Operation(summary = "Recupera dados detalhados de um produto específico")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer productId) {
        log.info("Carregando detalhes do produto ID: {}", productId);
        ProductResponse productResponse = productService.getProductById(productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    @Operation(summary = "Lista produtos do catálogo com filtros dinâmicos e paginação")
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "brandId", required = false) Integer brandId,
            @RequestParam(name = "typeId", required = false) Integer typeId,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "order", defaultValue = "asc") String order) {
        
        log.info("Filtragem de catálogo acionada - Termo: '{}', Brand: {}, Type: {}", keyword, brandId, typeId);
        
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sorting = Sort.by(direction, sort);
        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<ProductResponse> productResponses = productService.getProducts(pageable, brandId, typeId, keyword);
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/brands")
    @Operation(summary = "Lista todas as marcas registradas para alimentar a barra lateral de filtros")
    public ResponseEntity<List<BrandResponse>> getBrands() {
        log.info("Buscando lista completa de marcas.");
        List<BrandResponse> brandResponses = brandService.getAllBrands();
        return ResponseEntity.ok(brandResponses);
    }

    @GetMapping("/types")
    @Operation(summary = "Lista todas as categorias/tipos de produtos do sistema")
    public ResponseEntity<List<TypeResponse>> getTypes() {
        log.info("Buscando lista completa de categorias.");
        List<TypeResponse> typeResponses = typeService.getAllTypes();
        return ResponseEntity.ok(typeResponses);
    }
}