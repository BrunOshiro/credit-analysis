package com.jazztech.creditanalysis.infrastructure.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

@Table(name = "credit_analysis")
@Entity
@Immutable
@Getter
@Setter
public class CreditAnalysisEntity {
    private static final Integer ROUND = 2;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "client_id")
    UUID clientId;

    @Column(name = "client_cpf")
    String clientCpf;

    @Column(name = "client_name")
    String clientName;

    @Column(name = "monthly_income")
    BigDecimal monthlyIncome;

    @Column(name = "requested_amount")
    BigDecimal requestedAmount;

    @Column(name = "approved")
    Boolean approved;

    @Column(name = "approved_limit")
    BigDecimal approvedLimit;

    @Column(name = "annual_interest")
    BigDecimal annualInterest;

    @Column(name = "withdraw")
    BigDecimal withdraw;

    @CreationTimestamp
    @Column(name = "creation_date")
    LocalDateTime creationDate;

    public CreditAnalysisEntity() {
    }

    @Builder(toBuilder = true)
    public CreditAnalysisEntity(UUID clientId,
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
        this.monthlyIncome = monthlyIncome.setScale(ROUND, RoundingMode.HALF_UP);
        this.requestedAmount = requestedAmount.setScale(ROUND, RoundingMode.HALF_UP);
        this.approved = approved;
        this.approvedLimit = approvedLimit.setScale(ROUND, RoundingMode.HALF_UP);
        this.annualInterest = annualInterest.setScale(ROUND, RoundingMode.HALF_UP);
        this.withdraw = withdraw.setScale(ROUND, RoundingMode.HALF_UP);
        this.creationDate = LocalDateTime.now();
    }
}
