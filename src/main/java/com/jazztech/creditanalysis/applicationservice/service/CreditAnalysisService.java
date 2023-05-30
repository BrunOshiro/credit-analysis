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
    private static final Double MAX_MONTHLY_INCOME = 50000.00;
    private static final Double MAX_APPROVAL_PERCENTAGE = 0.30;
    private static final Double MIN_APPROVAL_PERCENTAGE = 0.15;
    private static final Double WITHDRAW_LIMIT = 0.10;
    private static final Double ANNUAL_INTEREST = 0.15;

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
        final CreditAnalysisDomain newCreditAnalysisCreditAnalysisDomain = performCreditAnalysis(newClientCreditAnalysisDomain);

        //Save into database
        final CreditAnalysisEntity creditAnalysisEntity = mapper.domainToEntity(newCreditAnalysisCreditAnalysisDomain);
        final CreditAnalysisEntity savedAnalysis = creditAnalysisRepository.save(creditAnalysisEntity);

        LOGGER.info("Credit Analysis was performed successfully");
        return mapper.entityToDto(savedAnalysis);
    }

    // Consult ClientApi
    private ClientApiDto getClientFromClientApi(UUID clientId) throws ClientNotFound {
        final ClientApiDto clientApiDto = clientApi.getClientById(clientId);
        if (clientApiDto.id() == null) {
            throw new ClientNotFound("Client not found for the ID %s", clientId);
        }
        LOGGER.info("Client consulted successfully");
        return clientApiDto;
    }

    // Credit Analysis
    public CreditAnalysisDomain performCreditAnalysis(CreditAnalysisDomain updatedClientCreditAnalysisDomain) {
        final Double requestedAmount = updatedClientCreditAnalysisDomain.requestedAmount();
        Double monthlyIncome = updatedClientCreditAnalysisDomain.monthlyIncome();

        // Check if monthlyIncome is greater than maximum allowed
        monthlyIncome = (monthlyIncome > MAX_MONTHLY_INCOME) ? MAX_MONTHLY_INCOME : monthlyIncome;

        // Approval
        final boolean approved = requestedAmount <= monthlyIncome;

        // Calculate the approved limit
        double approvedLimit = 0.00;
        if (requestedAmount <= monthlyIncome) {
            approvedLimit = (requestedAmount > monthlyIncome * 0.5)
                    ? monthlyIncome * MIN_APPROVAL_PERCENTAGE
                    : monthlyIncome * MAX_APPROVAL_PERCENTAGE;
        }

        final Double withdraw = approvedLimit * WITHDRAW_LIMIT;

        // Setting values to domain
        if (approved) {
            updatedClientCreditAnalysisDomain = updatedClientCreditAnalysisDomain.toBuilder()
                    .approved(true)
                    .approvedLimit(approvedLimit)
                    .annualInterest(ANNUAL_INTEREST)
                    .withdraw(withdraw)
                    .build();
        } else {
            updatedClientCreditAnalysisDomain = updatedClientCreditAnalysisDomain.toBuilder()
                    .approved(false)
                    .approvedLimit(0.00)
                    .annualInterest(0.00)
                    .withdraw(0.00)
                    .build();
        }

        return updatedClientCreditAnalysisDomain;
    }
}
