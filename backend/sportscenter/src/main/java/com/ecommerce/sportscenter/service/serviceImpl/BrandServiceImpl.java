package com.ecommerce.sportscenter.service.serviceImpl;

import com.ecommerce.sportscenter.dto.BrandResponse;
import com.ecommerce.sportscenter.entity.Brand;
import com.ecommerce.sportscenter.repository.BrandRepository;
import com.ecommerce.sportscenter.service.BrandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor // Injeção de dependência automática via construtor do Lombok
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    @Transactional(readOnly = true) // Otimiza a sessão do Hibernate para consultas puras no Postgres
    public List<BrandResponse> getAllBrands() {
        log.info("Fetching All Brands!!!");
        
        List<Brand> brandList = brandRepository.findAll();
        
        // Uso do .toList() nativo do Java 21 para maior performance e imutabilidade
        List<BrandResponse> brandResponses = brandList.stream()
                .map(this::convertToBrandResponse)
                .toList();
                
        log.info("Fetched All Brands Successfully. Total: {}", brandResponses.size());
        return brandResponses;
    }

    private BrandResponse convertToBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}