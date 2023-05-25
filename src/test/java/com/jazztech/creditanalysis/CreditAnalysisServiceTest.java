package com.jazztech.creditanalysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.jazztech.creditanalysis.applicationservice.domain.entity.Domain;
import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysis;
import com.jazztech.creditanalysis.infrastructure.clientsapi.ClientApi;
import com.jazztech.creditanalysis.infrastructure.clientsapi.dto.ClientApiDto;
import com.jazztech.creditanalysis.infrastructure.repository.MapperImpl;
import com.jazztech.creditanalysis.infrastructure.repository.Repository;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
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
    private MapperImpl mapperImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        creditAnalysis = new CreditAnalysis(repository, clientApi, mapperImpl);
    }

    @Test
    public void should_get_client_from_client_api() {
        // Client existent in client api
        UUID clientId = UUID.fromString("dd1efc87-a9c1-4f08-8fb7-1cd71d92dd6d");

        // Expected return
        ClientApiDto expectedClientApiDto = Factory.clientApiDtoFactory();

        // Mock behaviour
        when(clientApi.getClientById(clientId)).thenReturn(expectedClientApiDto);

        // Method call
        ClientApiDto clientApiDto = clientApi.getClientById(clientId);

        // Assert
        assertEquals(expectedClientApiDto, clientApiDto);
    }

    @Test
    public void should_get_credit_analysis_approved() {
        // Create test domain object
        RequestDto requestDto;
        requestDto = Factory.requestDtoFactory().builder()
                .clientId(UUID.fromString("dd1efc87-a9c1-4f08-8fb7-1cd71d92dd6d"))
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
    public void should_get_credit_analysis_not_approved() {
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

    @Test
    public void should_get_credit_analysis_approved_min_amount() {
        // Create test domain object
        RequestDto requestDto;
        requestDto = Factory.requestDtoFactory().builder()
                .monthlyIncome(BigDecimal.valueOf(10000.00))
                .requestedAmount(BigDecimal.valueOf(5001.00))
                .build();
        Domain domain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        Domain result = creditAnalysis.getCreditAnalysis(domain);

        // Assert the expected values
        assertEquals(true, result.approved());
        assertEquals(1500.00, result.approvedLimit());
        assertEquals(150.00, result.withdraw());
        assertEquals(0.15, result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_max_monthly_income_max_approval() {
        // Create test domain object
        RequestDto requestDto;
        requestDto = Factory.requestDtoFactory().builder()
                .monthlyIncome(BigDecimal.valueOf(75000.00))
                .requestedAmount(BigDecimal.valueOf(20000.00))
                .build();
        Domain domain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        Domain result = creditAnalysis.getCreditAnalysis(domain);

        // Assert the expected values
        assertEquals(true, result.approved());
        assertEquals(15000.00, result.approvedLimit());
        assertEquals(1500.00, result.withdraw());
        assertEquals(0.15, result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_max_monthly_income_min_approval() {
        // Create test domain object
        RequestDto requestDto;
        requestDto = Factory.requestDtoFactory().builder()
                .monthlyIncome(BigDecimal.valueOf(100000.00))
                .requestedAmount(BigDecimal.valueOf(50000.00))
                .build();
        Domain domain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        Domain result = creditAnalysis.getCreditAnalysis(domain);

        // Assert the expected values
        assertEquals(true, result.approved());
        assertEquals(7500.00, result.approvedLimit());
        assertEquals(750.00, result.withdraw());
        assertEquals(0.15, result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_max_monthly_income_not_approved() {
        // Create test domain object
        RequestDto requestDto;
        requestDto = Factory.requestDtoFactory().builder()
                .monthlyIncome(BigDecimal.valueOf(100000.00))
                .requestedAmount(BigDecimal.valueOf(51000.00))
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
