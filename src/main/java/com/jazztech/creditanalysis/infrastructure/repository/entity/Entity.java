package com.jazztech.creditanalysis.infrastructure.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

@Table(name = "credit_analysis")
@jakarta.persistence.Entity
@Immutable
public class Entity {
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

    public Entity() {
    }

    public Entity(UUID id,
                  UUID clientId,
                  String clientCpf,
                  String clientName,
                  BigDecimal monthlyIncome,
                  BigDecimal requestedAmount,
                  Boolean approved,
                  BigDecimal approvedLimit,
                  BigDecimal annualInterest,
                  BigDecimal withdraw,
                  LocalDateTime creationDate
    ) {
        this.id = id;
        this.clientId = clientId;
        this.clientCpf = clientCpf;
        this.clientName = clientName;
        this.monthlyIncome = monthlyIncome.setScale(ROUND, RoundingMode.HALF_UP);
        this.requestedAmount = requestedAmount.setScale(ROUND, RoundingMode.HALF_UP);
        this.approved = approved;
        this.approvedLimit = approvedLimit.setScale(ROUND, RoundingMode.HALF_UP);
        this.annualInterest = annualInterest.setScale(ROUND, RoundingMode.HALF_UP);
        this.withdraw = withdraw.setScale(ROUND, RoundingMode.HALF_UP);
        this.creationDate = creationDate;
    }


    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public BigDecimal getApprovedLimit() {
        return approvedLimit;
    }

    public BigDecimal getAnnualInterest() {
        return annualInterest;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
