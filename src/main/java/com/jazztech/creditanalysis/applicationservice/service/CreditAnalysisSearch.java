package com.jazztech.creditanalysis.applicationservice.service;

import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisMapper;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisRepository;
import com.jazztech.creditanalysis.infrastructure.repository.entity.CreditAnalysisEntity;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreditAnalysisSearch {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditAnalysisSearch.class);
    private final CreditAnalysisRepository creditAnalysisRepository;
    private final CreditAnalysisMapper mapper;

    @Transactional
    public List<ResponseDto> byCpf(@Valid String clientCpf) {
        final List<CreditAnalysisEntity> entities = StringUtils.isBlank(clientCpf)
                ? creditAnalysisRepository.findAll() : creditAnalysisRepository.findByClientCpf(clientCpf);
        LOGGER.info("Credit Analysis Listed by CPF Successfully");
        return mapper.listEntityToListDto(entities);
    }

    @Transactional
    public List<ResponseDto> byClientId(UUID clientId) {
        final List<CreditAnalysisEntity> entities = creditAnalysisRepository.findByClientId(clientId);
        LOGGER.info("Credit Analysis listed by ClientId successfully");
        return mapper.listEntityToListDto(entities);
    }

    @Transactional
    public List<ResponseDto> all() {
        final List<CreditAnalysisEntity> entities = creditAnalysisRepository.findAll();
        LOGGER.info("Credit Analysis listed Successfully");
        return mapper.listEntityToListDto(entities);
    }
}
