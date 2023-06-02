package com.jazztech.creditanalysis.applicationservice.service;

import com.jazztech.creditanalysis.applicationservice.domain.entity.CreditAnalysisDomain;
import com.jazztech.creditanalysis.infrastructure.clientsapi.ClientApi;
import com.jazztech.creditanalysis.infrastructure.clientsapi.dto.ClientApiDto;
import com.jazztech.creditanalysis.infrastructure.exceptions.ClientNotFound;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisMapper;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisRepository;
import com.jazztech.creditanalysis.infrastructure.repository.entity.CreditAnalysisEntity;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditAnalysisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditAnalysisService.class);

    private final CreditAnalysisRepository creditAnalysisRepository;
    private final ClientApi clientApi;
    private final CreditAnalysisMapper mapper;

    @Transactional
    public ResponseDto createCreditAnalysis(@Valid RequestDto requestDto) throws ClientNotFound {
        // Client consult
        final CreditAnalysisDomain creditAnalysisDomain = mapper.dtoToDomain(requestDto);
        final ClientApiDto clientApiDto = getClientFromClientApi(creditAnalysisDomain.clientId());
        final CreditAnalysisDomain newClientCreditAnalysisDomain = creditAnalysisDomain.updateDomain(clientApiDto);

        //Credit Analysis
        final CreditAnalysisDomain newCreditAnalysisCreditAnalysisDomain = creditAnalysisDomain.performCreditAnalysis(newClientCreditAnalysisDomain);

        //Save into database
        final CreditAnalysisEntity creditAnalysisEntity = mapper.domainToEntity(newCreditAnalysisCreditAnalysisDomain);
        final CreditAnalysisEntity savedAnalysis = creditAnalysisRepository.save(creditAnalysisEntity);

        LOGGER.info("Credit Analysis was performed successfully");
        return mapper.entityToDto(savedAnalysis);
    }

    // Consult ClientApi
    private ClientApiDto getClientFromClientApi(UUID clientId) throws ClientNotFound {
        final ClientApiDto clientApiDto = clientApi.getClientById(clientId);
        // Na config do feign não esta com "dismiss404=true", sendo assim caso de client notfound não irá retornar null
        if (clientApiDto.id() == null) {
            throw new ClientNotFound("Client not found for the ID %s", clientId);
        }
        // Este log é desnecessário
        LOGGER.info("Client consulted successfully");
        return clientApiDto;
    }
}
