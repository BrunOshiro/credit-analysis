package com.jazztech.creditanalysis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jazztech.creditanalysis.applicationservice.domain.entity.CreditAnalysisDomain;
import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysisService;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisMapperImpl;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.infrastructure.Factory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CreditAnalysisServiceServiceTest {
    private static final Integer ROUND = 2;

    @InjectMocks
    CreditAnalysisService creditAnalysisService;
    @Spy
    private CreditAnalysisMapperImpl mapperImpl;

    @Test
    public void should_get_credit_analysis_approved() {
        // Create test domain object
        RequestDto requestDto = Factory.requestDtoFactory().toBuilder()
                .monthlyIncome(BigDecimal.valueOf(10000.00))
                .requestedAmount(BigDecimal.valueOf(5000.00))
                .build();
        CreditAnalysisDomain creditAnalysisDomain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        CreditAnalysisDomain result = creditAnalysisDomain.performCreditAnalysis(creditAnalysisDomain);

        //Assert the expected values
        assertEquals(true, result.approved());
        assertEquals(BigDecimal.valueOf(3000.00).setScale(ROUND, RoundingMode.HALF_UP), result.approvedLimit());
        assertEquals(BigDecimal.valueOf(300.00).setScale(ROUND,RoundingMode.HALF_UP), result.withdraw());
        assertEquals(BigDecimal.valueOf(0.15), result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_not_approved() {
        // Create test domain object
        RequestDto requestDto = Factory.requestDtoFactory().toBuilder()
                .monthlyIncome(BigDecimal.valueOf(5000.00))
                .requestedAmount(BigDecimal.valueOf(7000.00))
                .build();
        CreditAnalysisDomain creditAnalysisDomain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        CreditAnalysisDomain result = creditAnalysisDomain.performCreditAnalysis(creditAnalysisDomain);

        // Assert the expected values
        assertEquals(false, result.approved());
        assertEquals(BigDecimal.valueOf(0.00), result.approvedLimit());
        assertEquals(BigDecimal.valueOf(0.00), result.withdraw());
        assertEquals(BigDecimal.valueOf(0.00), result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_min_amount() {
        // Create test domain object
        RequestDto requestDto = Factory.requestDtoFactory()
                .toBuilder()
                .monthlyIncome(BigDecimal.valueOf(10000.00))
                .requestedAmount(BigDecimal.valueOf(5001.00))
                .build();
        CreditAnalysisDomain creditAnalysisDomain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        CreditAnalysisDomain result = creditAnalysisDomain.performCreditAnalysis(creditAnalysisDomain);

        // Assert the expected values
        assertEquals(true, result.approved());
        assertEquals(BigDecimal.valueOf(1500.00).setScale(ROUND, RoundingMode.HALF_UP), result.approvedLimit());
        assertEquals(BigDecimal.valueOf(150.00).setScale(ROUND, RoundingMode.HALF_UP), result.withdraw());
        assertEquals(BigDecimal.valueOf(0.15), result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_max_monthly_income_max_approval() {
        // Create test domain object
        RequestDto requestDto = Factory.requestDtoFactory().toBuilder()
                .monthlyIncome(BigDecimal.valueOf(75000.00))
                .requestedAmount(BigDecimal.valueOf(20000.00))
                .build();
        CreditAnalysisDomain creditAnalysisDomain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        CreditAnalysisDomain result = creditAnalysisDomain.performCreditAnalysis(creditAnalysisDomain);

        // Assert the expected values
        assertEquals(true, result.approved());
        assertEquals(BigDecimal.valueOf(15000.00).setScale(ROUND, RoundingMode.HALF_UP), result.approvedLimit());
        assertEquals(BigDecimal.valueOf(1500.00).setScale(ROUND, RoundingMode.HALF_UP), result.withdraw());
        assertEquals(BigDecimal.valueOf(0.15), result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_max_monthly_income_min_approval() {
        // Create test domain object
        RequestDto requestDto = Factory.requestDtoFactory()
                .toBuilder()
                .monthlyIncome(BigDecimal.valueOf(100000.00))
                .requestedAmount(BigDecimal.valueOf(50000.00))
                .build();
        CreditAnalysisDomain creditAnalysisDomain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        CreditAnalysisDomain result = creditAnalysisDomain.performCreditAnalysis(creditAnalysisDomain);

        // Assert the expected values
        assertEquals(true, result.approved());
        assertEquals(BigDecimal.valueOf(7500.00).setScale(ROUND, RoundingMode.HALF_UP), result.approvedLimit());
        assertEquals(BigDecimal.valueOf(750.00).setScale(ROUND, RoundingMode.HALF_UP), result.withdraw());
        assertEquals(BigDecimal.valueOf(0.15), result.annualInterest());
    }

    @Test
    public void should_get_credit_analysis_approved_max_monthly_income_not_approved() {
        // Create test domain object
        RequestDto requestDto;
        requestDto = Factory.requestDtoFactory()
                .toBuilder()
                .monthlyIncome(BigDecimal.valueOf(100000.00))
                .requestedAmount(BigDecimal.valueOf(51000.00))
                .build();
        CreditAnalysisDomain creditAnalysisDomain = mapperImpl.dtoToDomain(requestDto);

        // Call method to being tested
        CreditAnalysisDomain result = creditAnalysisDomain.performCreditAnalysis(creditAnalysisDomain);

        // Assert the expected values
        assertEquals(false, result.approved());
        assertEquals(BigDecimal.valueOf(0.00), result.approvedLimit());
        assertEquals(BigDecimal.valueOf(0.00), result.withdraw());
        assertEquals(BigDecimal.valueOf(0.00), result.annualInterest());
    }
}
