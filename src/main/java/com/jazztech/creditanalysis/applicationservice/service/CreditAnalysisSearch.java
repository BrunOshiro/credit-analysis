package com.jazztech.creditanalysis.applicationservice.service;

import com.jazztech.creditanalysis.infrastructure.exceptions.CreditAnalysisNotFound;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisMapper;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisRepository;
import com.jazztech.creditanalysis.infrastructure.repository.entity.CreditAnalysisEntity;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreditAnalysisSearch {
    private final CreditAnalysisRepository creditAnalysisRepository;
    private final CreditAnalysisMapper mapper;

    public List<ResponseDto> byCpf(@Valid String clientCpf) {
        final List<CreditAnalysisEntity> entities = creditAnalysisRepository.findByClientCpf(clientCpf);
        return mapper.listEntityToListDto(entities);
    }

    public List<ResponseDto> byClientId(UUID clientId) {
        final List<CreditAnalysisEntity> entities = creditAnalysisRepository.findByClientId(clientId);
        return mapper.listEntityToListDto(entities);
    }

    public ResponseDto byId(UUID id) {
        final CreditAnalysisEntity entity = creditAnalysisRepository.findById(id)
                .orElseThrow(() -> new CreditAnalysisNotFound("Credit Analysis " + id + " not found"));
        return mapper.entityToDto(entity);
    }

    public List<ResponseDto> all() {
        final List<CreditAnalysisEntity> entities = creditAnalysisRepository.findAll();
        return mapper.listEntityToListDto(entities);
    }
}
