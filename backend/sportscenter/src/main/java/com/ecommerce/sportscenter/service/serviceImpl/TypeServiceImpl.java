package com.ecommerce.sportscenter.service.serviceImpl;

import com.ecommerce.sportscenter.dto.TypeResponse;
import com.ecommerce.sportscenter.entity.Type;
import com.ecommerce.sportscenter.repository.TypeRepository;
import com.ecommerce.sportscenter.service.TypeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor // Injeção automática via construtor do Lombok para o repository final
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    @Override
    @Transactional(readOnly = true) // Desativa o gerenciamento de estado do Hibernate, acelerando a busca no Postgres
    public List<TypeResponse> getAllTypes() {
        log.info("Fetching All Types!!!");
        
        List<Type> typeList = typeRepository.findAll();
        
        // Substituído pelo .toList() nativo do Java 21 para maior eficiência e imutabilidade
        List<TypeResponse> typeResponses = typeList.stream()
                .map(this::convertToTypeResponse)
                .toList();
                
        log.info("Fetched All Types Successfully. Total: {}", typeResponses.size());
        return typeResponses;
    }

    private TypeResponse convertToTypeResponse(Type type) {
        return TypeResponse.builder()
                .id(type.getId())
                .name(type.getName())
                .build();
    }
}