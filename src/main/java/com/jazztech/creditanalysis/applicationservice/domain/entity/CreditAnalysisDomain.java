package com.jazztech.creditanalysis.applicationservice.domain.entity;

import com.jazztech.creditanalysis.infrastructure.clientsapi.dto.ClientApiDto;
import com.jazztech.creditanalysis.infrastructure.repository.util.ValidationCustom;
import java.util.UUID;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

public record CreditAnalysisDomain(
        UUID clientId,
        @CPF
        String clientCpf,
        String clientName,
        Double monthlyIncome,
        Double requestedAmount,
        Boolean approved,
        Double approvedLimit,
        Double annualInterest,
        Double withdraw
) {
    private static final Double MAX_MONTHLY_INCOME = 50000.00;
    private static final Double MAX_APPROVAL_PERCENTAGE = 0.30;
    private static final Double MIN_APPROVAL_PERCENTAGE = 0.15;
    private static final Double WITHDRAW_LIMIT = 0.10;
    private static final Double ANNUAL_INTEREST = 0.15;

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

    @Builder(toBuilder = true)
    public CreditAnalysisDomain(
            UUID clientId,
            String clientCpf,
            String clientName,
            //Para valores monetários utilize BigDecimal
            Double monthlyIncome,
            Double requestedAmount,
            Boolean approved,
            Double approvedLimit,
            Double annualInterest,
            Double withdraw
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

    // melhor criar métodos especificos e mais claros, o que esta sendo atualizado?
    //Data update after ClientApi search
    public CreditAnalysisDomain updateDomain(ClientApiDto clientApiDto) {
        // utilizando o toBuilder do lombok, não precisa atualizar todos os campos, apenas os que mudaram
        return this.toBuilder()
                .clientId(this.clientId())
                .clientCpf(clientApiDto.cpf())
                .clientName(clientApiDto.nome())
                .monthlyIncome(this.monthlyIncome())
                .requestedAmount(this.requestedAmount())
                .approved(this.approved())
                .approvedLimit(this.approvedLimit())
                .annualInterest(this.annualInterest())
                .withdraw(this.withdraw())
                .build();
    }
}
