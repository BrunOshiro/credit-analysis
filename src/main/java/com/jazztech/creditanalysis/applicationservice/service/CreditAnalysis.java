package com.jazztech.creditanalysis.applicationservice.service;

import com.jazztech.creditanalysis.applicationservice.domain.entity.Domain;
import com.jazztech.creditanalysis.infrastructure.clientsapi.ClientApi;
import com.jazztech.creditanalysis.infrastructure.clientsapi.dto.ClientApiDto;
import com.jazztech.creditanalysis.infrastructure.exceptions.ClientNotFound;
import com.jazztech.creditanalysis.infrastructure.repository.Mapper;
import com.jazztech.creditanalysis.infrastructure.repository.Repository;
import com.jazztech.creditanalysis.infrastructure.repository.entity.Entity;
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
public class CreditAnalysis {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditAnalysis.class);
    private final Repository repository;
    private final ClientApi clientApi;
    private final Mapper mapper;

    @Transactional
    public ResponseDto create(@Valid RequestDto requestDto) throws ClientNotFound {
        // Client consult
        final Domain domain = mapper.dtoToDomain(requestDto);
        final ClientApiDto clientApiDto = getClientFromClientApi(domain.clientId());
        final Domain updatedClientDomain = domain.updateDomain(clientApiDto);

        //Credit Analysis
        final Domain updatedCreditAnalysisDomain = getCreditAnalysis(updatedClientDomain);

        //Save into database
        final Entity entity = mapper.domainToEntity(updatedCreditAnalysisDomain);
        final Entity savedAnalysis = repository.save(entity);

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
    private Domain getCreditAnalysis(Domain updatedClientDomain) {
        final Double requestedAmount = updatedClientDomain.requestedAmount();
        Double monthlyIncome = updatedClientDomain.monthlyIncome();
        final Double maxMonthlyIncome = 50000.00;
        final Double maxApprovalPercentage = .30;
        final Double minApprovalPercentage = .15;
        final double withdrawLimit = .1;
        final Double annualInterest = .15;

        // Check if monthlyIncome is greater than maximum allowed
        monthlyIncome = (monthlyIncome > maxMonthlyIncome) ? maxMonthlyIncome : monthlyIncome;

        // Calculate the approved limit
        final double approvedLimit;
        if (requestedAmount < monthlyIncome) {
            if (requestedAmount > monthlyIncome * .5) {
                approvedLimit = monthlyIncome * minApprovalPercentage;
            } else {
                approvedLimit = monthlyIncome * maxApprovalPercentage;
            }
        } else {
            approvedLimit = 0.00;
        }

        final Double withdraw = approvedLimit * withdrawLimit;

        // Setting values to domain
        updatedClientDomain = updatedClientDomain.toBuilder()
                .approvedLimit(approvedLimit)
                .annualInterest(annualInterest)
                .withdraw(withdraw)
                .build();
        return updatedClientDomain;
    }
}
