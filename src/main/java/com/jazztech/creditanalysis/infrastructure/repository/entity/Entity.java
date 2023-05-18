package com.jazztech.creditanalysis.infrastructure.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

@Table(name = "credit_analysis")
@jakarta.persistence.Entity
@Immutable
public class Entity {
    @Id
    UUID id;
    @Column(name = "client_id")
    UUID clientId;
    @Column(name = "client_cpf")
    String clientCpf;
    @Column(name = "client_name")
    String clientName;
    @Column(name = "monthly_income")
    Double monthlyIncome;
    @Column(name = "requested_amount")
    Double requestedAmount;
    @Column(name = "approved_limit")
    Double approvedLimit;
    @Column(name = "annual_interest")
    Double annualInterest;
    @Column(name = "withdraw")
    Double withdraw;
    @CreationTimestamp
    @Column(name = "creation_date")
    LocalDate creationDate;

    private Entity() {
    }

    public Entity(
            UUID id,
            UUID clientId,
            String clientCpf,
            String clientName,
            Double monthlyIncome,
            Double requestedAmount,
            Double approvedLimit,
            Double annualInterest,
            Double withdraw,
            LocalDate creationDate
    ) {
        this.id = UUID.randomUUID();
        this.clientId = clientId;
        this.clientCpf = clientCpf;
        this.clientName = clientName;
        this.monthlyIncome = monthlyIncome;
        this.requestedAmount = requestedAmount;
        this.approvedLimit = approvedLimit;
        this.annualInterest = annualInterest;
        this.withdraw = withdraw;
        this.creationDate = creationDate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public String getClientCpf() {
        return clientCpf;
    }

    public String getClientName() {
        return clientName;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public Double getRequestedAmount() {
        return requestedAmount;
    }

    public Double getApprovedLimit() {
        return approvedLimit;
    }

    public Double getAnnualInterest() {
        return annualInterest;
    }

    public Double getWithdraw() {
        return withdraw;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
