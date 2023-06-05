package com.jazztech.creditanalysis.applicationservice.domain.entity;

import com.jazztech.creditanalysis.infrastructure.clientsapi.dto.ClientApiDto;
import com.jazztech.creditanalysis.infrastructure.repository.util.ValidationCustom;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

public record CreditAnalysisDomain(
        UUID clientId,
        @CPF
        String clientCpf,
        String clientName,
        BigDecimal monthlyIncome,
        BigDecimal requestedAmount,
        Boolean approved,
        BigDecimal approvedLimit,
        BigDecimal annualInterest,
        BigDecimal withdraw
) {
    private static final BigDecimal MAX_MONTHLY_INCOME = BigDecimal.valueOf(50000.00);
    private static final BigDecimal MAX_APPROVAL_PERCENTAGE = BigDecimal.valueOf(0.30);
    private static final BigDecimal MIN_APPROVAL_PERCENTAGE = BigDecimal.valueOf(0.15);
    private static final BigDecimal WITHDRAW_LIMIT = BigDecimal.valueOf(0.10);
    private static final BigDecimal ANNUAL_INTEREST = BigDecimal.valueOf(0.15);

    @Builder(toBuilder = true)
    public CreditAnalysisDomain(
            UUID clientId,
            String clientCpf,
            String clientName,
            BigDecimal monthlyIncome,
            BigDecimal requestedAmount,
            Boolean approved,
            BigDecimal approvedLimit,
            BigDecimal annualInterest,
            BigDecimal withdraw
    ) {
        this.clientId = clientId;
        this.clientCpf = clientCpf;
        this.clientName = clientName;
        this.monthlyIncome = monthlyIncome;
        this.requestedAmount = requestedAmount;
        this.approved = approved;
        this.approvedLimit = approvedLimit;
        this.annualInterest = annualInterest;
        this.withdraw = withdraw;
        ValidationCustom.validator(this);
    }

    // Credit Analysis
    public CreditAnalysisDomain performCreditAnalysis(CreditAnalysisDomain updatedClientCreditAnalysisDomain) {
        final BigDecimal requestedAmount = updatedClientCreditAnalysisDomain.requestedAmount();
        BigDecimal monthlyIncome = updatedClientCreditAnalysisDomain.monthlyIncome();

        monthlyIncome = (monthlyIncome.compareTo(MAX_MONTHLY_INCOME) <= 0)
                ? MAX_MONTHLY_INCOME
                : monthlyIncome;

        final boolean approved = requestedAmount.compareTo(monthlyIncome) <= 0;

        BigDecimal approvedLimit = BigDecimal.valueOf(0.00);
        if (requestedAmount.compareTo(monthlyIncome) <= 0) {
            approvedLimit = (requestedAmount.compareTo(monthlyIncome.multiply(BigDecimal.valueOf(0.5))) > 0)
                    ? monthlyIncome.multiply(MIN_APPROVAL_PERCENTAGE)
                    : monthlyIncome.multiply(MAX_APPROVAL_PERCENTAGE);
        }

        final BigDecimal withdraw = approvedLimit.multiply(WITHDRAW_LIMIT);

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
                    .approvedLimit(BigDecimal.valueOf(0.00))
                    .annualInterest(BigDecimal.valueOf(0.00))
                    .withdraw(BigDecimal.valueOf(0.00))
                    .build();
        }

        return updatedClientCreditAnalysisDomain;
    }

    public CreditAnalysisDomain updateDomainWithCpfNameFromClientApiData(ClientApiDto clientApiDto) {
        return this.toBuilder()
                .clientCpf(clientApiDto.cpf())
                .clientName(clientApiDto.nome())
                .build();
    }
}
