package com.jazztech.creditanalysis.infrastructure.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@Table(name = "credit_analysis")
@Entity
public class CreditAnalysisEntity {
    private static final Integer ROUND = 2;
    @Id
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
        this.id = UUID.randomUUID();
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


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public BigDecimal getApprovedLimit() {
        return approvedLimit;
    }

    public void setApprovedLimit(BigDecimal approvedLimit) {
        this.approvedLimit = approvedLimit;
    }

    public BigDecimal getAnnualInterest() {
        return annualInterest;
    }

    public void setAnnualInterest(BigDecimal annualInterest) {
        this.annualInterest = annualInterest;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(BigDecimal withdraw) {
        this.withdraw = withdraw;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setClientCpf(String clientCpf) {
        this.clientCpf = clientCpf;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
}
