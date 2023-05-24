package com.jazztech.creditanalysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jazztech.creditanalysis.applicationservice.domain.entity.Domain;
import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysis;
import com.jazztech.creditanalysis.infrastructure.clientsapi.ClientApi;
import com.jazztech.creditanalysis.infrastructure.clientsapi.dto.ClientApiDto;
import com.jazztech.creditanalysis.infrastructure.exceptions.ClientNotFound;
import com.jazztech.creditanalysis.infrastructure.repository.Mapper;
import com.jazztech.creditanalysis.infrastructure.repository.Repository;
import com.jazztech.creditanalysis.infrastructure.repository.entity.Entity;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import com.jazztech.infrastructure.Factory;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CreditAnalysisServiceTest {
    @InjectMocks
    CreditAnalysis creditAnalysis;
    @Mock
    private Repository repository;
    @Mock
    private ClientApi clientApi;
    @Spy
    private Mapper mapperImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        creditAnalysis = new CreditAnalysis(repository, clientApi, mapperImpl);
    }

    @Test
    public void test_create_credit_analysis() throws ClientNotFound {

        // Mock data
        RequestDto requestDto = Factory.requestDtoFactory();
        Domain domain = mapperImpl.dtoToDomain(requestDto);
        ClientApiDto clientApiDto = Factory.clientApiDtoFactory();
        Domain newClientDomain = domain.updateDomain(clientApiDto);
        Domain newCreditAnalysisDomain = creditAnalysis.getCreditAnalysis(newClientDomain);
        Entity entity = mapperImpl.domainToEntity(newCreditAnalysisDomain);
        Entity savedAnalysis = Factory.entityFactory();
        ResponseDto expectedResponseDto = mapperImpl.entityToDto(savedAnalysis);

        // Mock dependencies
        when(mapperImpl.dtoToDomain(requestDto)).thenReturn(domain);
        when(clientApi.getClientById(any(UUID.class))).thenReturn(clientApiDto);
        when(domain.updateDomain(clientApiDto)).thenReturn(newClientDomain);
        when(creditAnalysis.getCreditAnalysis(newClientDomain)).thenReturn(newCreditAnalysisDomain);
        when(repository.save(entity)).thenReturn(savedAnalysis);
        when(mapperImpl.entityToDto(savedAnalysis)).thenReturn(expectedResponseDto);

        // Perform the test
        ResponseDto responseDto = creditAnalysis.create(requestDto);

        // Verify the interactions and assertions
        verify(mapperImpl).dtoToDomain(requestDto);
        verify(clientApi).getClientById(any(UUID.class));
        verify(domain).updateDomain(clientApiDto);
        verify(creditAnalysis).getCreditAnalysis(newClientDomain);
        verify(mapperImpl).domainToEntity(newCreditAnalysisDomain);
        verify(repository).save(entity);
        verify(mapperImpl).entityToDto(savedAnalysis);
        assertEquals(expectedResponseDto, responseDto);
    }

    @Test
    public void test_get_credit_analysis_approved() {
        // Create test domain object
        RequestDto requestDto;
        requestDto = Factory.requestDtoFactory().builder()
                .monthlyIncome(BigDecimal.valueOf(10000.00))
                .requestedAmount(BigDecimal.valueOf(5000.00))
                .build();
        Domain domain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        Domain result = creditAnalysis.getCreditAnalysis(domain);

        //Assert the expected values
        assertEquals(true, result.approved());
        assertEquals(3000.00, result.approvedLimit());
        assertEquals(300.00, result.withdraw());
        assertEquals(0.15, result.annualInterest());
    }

    @Test
    public void test_get_credit_analysis_not_approved() {
        // Create test domain object
        RequestDto requestDto;
        requestDto = Factory.requestDtoFactory().builder()
                .monthlyIncome(BigDecimal.valueOf(5000.00))
                .requestedAmount(BigDecimal.valueOf(7000.00))
                .build();
        Domain domain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        Domain result = creditAnalysis.getCreditAnalysis(domain);

        // Assert the expected values
        assertEquals(false, result.approved());
        assertEquals(0.00, result.approvedLimit());
        assertEquals(0.00, result.withdraw());
        assertEquals(0.00, result.annualInterest());
    }
}
