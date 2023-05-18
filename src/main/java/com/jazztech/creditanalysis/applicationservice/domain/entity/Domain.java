package com.jazztech.creditanalysis.applicationservice.domain.entity;

import com.jazztech.creditanalysis.infrastructure.clientsapi.dto.ClientApiDto;
import com.jazztech.creditanalysis.infrastructure.repository.util.ValidationCustom;
import java.util.UUID;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

public record Domain(
        UUID clientId,
        @CPF
        String clientCpf,
        String clientName,
        Double monthlyIncome,
        Double requestedAmount,
        Double approvedLimit,
        Double annualInterest,
        Double withdraw
) {
    @Builder(toBuilder = true)
    public Domain(
            UUID clientId,
            String clientCpf,
            String clientName,
            Double monthlyIncome,
            Double requestedAmount,
            Double approvedLimit,
            Double annualInterest,
            Double withdraw
    ) {
        this.clientId = clientId;
        this.clientCpf = clientCpf;
        this.clientName = clientName;
        this.monthlyIncome = monthlyIncome;
        this.requestedAmount = requestedAmount;
        this.approvedLimit = approvedLimit;
        this.annualInterest = annualInterest;
        this.withdraw = withdraw;
        ValidationCustom.validator(this);
    }

    //Data update after ClientApi search
    public Domain updateDomain(ClientApiDto clientApiDto) {
        return this.toBuilder()
                .clientId(this.clientId())
                .clientCpf(clientApiDto.cpf())
                .clientName(clientApiDto.nome())
                .monthlyIncome(this.monthlyIncome())
                .requestedAmount(this.requestedAmount())
                .approvedLimit(this.approvedLimit())
                .annualInterest(this.annualInterest())
                .withdraw(this.withdraw())
                .build();
    }
}
