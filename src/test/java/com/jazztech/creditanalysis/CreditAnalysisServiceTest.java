package com.jazztech.creditanalysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.wildfly.common.Assert.assertTrue;

import com.jazztech.creditanalysis.applicationservice.domain.entity.CreditAnalysisDomain;
import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysisService;
import com.jazztech.creditanalysis.infrastructure.clientsapi.ClientApi;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisMapperImpl;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisRepository;
import com.jazztech.creditanalysis.infrastructure.repository.entity.CreditAnalysisEntity;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import com.jazztech.infrastructure.Factory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CreditAnalysisServiceTest {
    private static final Integer ROUND = 2;
    @InjectMocks
    CreditAnalysisService creditAnalysisService;
    @Mock
    private ClientApi clientApi;
    @Mock
    private CreditAnalysisRepository creditAnalysisRepository;
    @Spy
    private CreditAnalysisMapperImpl mapperImpl;
    @Captor
    private ArgumentCaptor<UUID> clientIdCaptor;
    @Captor
    private ArgumentCaptor<CreditAnalysisEntity> creditAnalysisEntityCaptor;

    @Test
    public void should_get_credit_analysis_approved_and_check_mappers_impl() {
        when(clientApi.getClientById(clientIdCaptor.capture()))
                .thenReturn(Factory.clientApiDtoFactory());
        when(creditAnalysisRepository.save(creditAnalysisEntityCaptor.capture()))
                .thenReturn(Factory.creditAnalysisEntityFactory());

        RequestDto requestDto = Factory.requestDtoFactory().toBuilder()
                .monthlyIncome(BigDecimal.valueOf(10000.00))
                .requestedAmount(BigDecimal.valueOf(5000.00))
                .build();

        ResponseDto responseDto = creditAnalysisService.createCreditAnalysis(requestDto);

        assertNotNull(responseDto);
        assertTrue(responseDto.approved());
        assertEquals(BigDecimal.valueOf(3000.00).setScale(ROUND, RoundingMode.HALF_UP), responseDto.approvedLimit());
        assertEquals(BigDecimal.valueOf(300.00).setScale(ROUND, RoundingMode.HALF_UP), responseDto.withdraw());
        assertEquals(BigDecimal.valueOf(0.15), responseDto.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_not_approved() {
        when(clientApi.getClientById(clientIdCaptor.capture()))
                .thenReturn(Factory.clientApiDtoFactory());
        when(creditAnalysisRepository.save(creditAnalysisEntityCaptor.capture()))
                .thenReturn(Factory.creditAnalysisEntityFactory().toBuilder()
                        .monthlyIncome(BigDecimal.valueOf(5000.00))
                        .requestedAmount(BigDecimal.valueOf(7000.00))
                        .approved(false)
                        .approvedLimit(BigDecimal.valueOf(0.00))
                        .annualInterest(BigDecimal.valueOf(0.00))
                        .withdraw(BigDecimal.valueOf(0.00))
                        .build());

        RequestDto requestDto = Factory.requestDtoFactory().toBuilder()
                .monthlyIncome(BigDecimal.valueOf(5000.00))
                .requestedAmount(BigDecimal.valueOf(7000.00))
                .build();

        ResponseDto responseDto = creditAnalysisService.createCreditAnalysis(requestDto);

        assertNotNull(responseDto);
        assertFalse(responseDto.approved());
        assertEquals(BigDecimal.valueOf(0.00).setScale(ROUND, RoundingMode.HALF_UP), responseDto.approvedLimit());
        assertEquals(BigDecimal.valueOf(0.00).setScale(ROUND, RoundingMode.HALF_UP), responseDto.withdraw());
        assertEquals(BigDecimal.valueOf(0.00).setScale(ROUND, RoundingMode.HALF_UP), responseDto.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_min_amount() {
        RequestDto requestDto = Factory.requestDtoFactory()
                .toBuilder()
                .monthlyIncome(BigDecimal.valueOf(10000.00))
                .requestedAmount(BigDecimal.valueOf(5001.00))
                .build();
        CreditAnalysisDomain creditAnalysisDomain = mapperImpl.dtoToDomain(requestDto);

        CreditAnalysisDomain result = creditAnalysisDomain.performCreditAnalysis(creditAnalysisDomain);

        assertEquals(true, result.approved());
        assertEquals(BigDecimal.valueOf(1500.00).setScale(ROUND, RoundingMode.HALF_UP), result.approvedLimit());
        assertEquals(BigDecimal.valueOf(150.00).setScale(ROUND, RoundingMode.HALF_UP), result.withdraw());
        assertEquals(BigDecimal.valueOf(0.15), result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_max_monthly_income_max_approval() {
        RequestDto requestDto = Factory.requestDtoFactory().toBuilder()
                .monthlyIncome(BigDecimal.valueOf(75000.00))
                .requestedAmount(BigDecimal.valueOf(20000.00))
                .build();
        CreditAnalysisDomain creditAnalysisDomain = mapperImpl.dtoToDomain(requestDto);

        CreditAnalysisDomain result = creditAnalysisDomain.performCreditAnalysis(creditAnalysisDomain);

        assertEquals(true, result.approved());
        assertEquals(BigDecimal.valueOf(15000.00).setScale(ROUND, RoundingMode.HALF_UP), result.approvedLimit());
        assertEquals(BigDecimal.valueOf(1500.00).setScale(ROUND, RoundingMode.HALF_UP), result.withdraw());
        assertEquals(BigDecimal.valueOf(0.15), result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_max_monthly_income_min_approval() {
        RequestDto requestDto = Factory.requestDtoFactory()
                .toBuilder()
                .monthlyIncome(BigDecimal.valueOf(100000.00))
                .requestedAmount(BigDecimal.valueOf(50000.00))
                .build();
        CreditAnalysisDomain creditAnalysisDomain = mapperImpl.dtoToDomain(requestDto);

        CreditAnalysisDomain result = creditAnalysisDomain.performCreditAnalysis(creditAnalysisDomain);

        assertEquals(true, result.approved());
        assertEquals(BigDecimal.valueOf(7500.00).setScale(ROUND, RoundingMode.HALF_UP), result.approvedLimit());
        assertEquals(BigDecimal.valueOf(750.00).setScale(ROUND, RoundingMode.HALF_UP), result.withdraw());
        assertEquals(BigDecimal.valueOf(0.15), result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_max_monthly_income_not_approved() {
        RequestDto requestDto;
        requestDto = Factory.requestDtoFactory()
                .toBuilder()
                .monthlyIncome(BigDecimal.valueOf(100000.00))
                .requestedAmount(BigDecimal.valueOf(51000.00))
                .build();
        CreditAnalysisDomain creditAnalysisDomain = mapperImpl.dtoToDomain(requestDto);

        CreditAnalysisDomain result = creditAnalysisDomain.performCreditAnalysis(creditAnalysisDomain);

        assertEquals(false, result.approved());
        assertEquals(BigDecimal.valueOf(0.00), result.approvedLimit());
        assertEquals(BigDecimal.valueOf(0.00), result.withdraw());
        assertEquals(BigDecimal.valueOf(0.00), result.annualInterest());
    }
}
