package com.jazztech.creditanalysis.infrastructure.clientsapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClientApiDto(
        UUID id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        ClientApiAddressDto clientApiAddressDto,
        LocalDateTime creationDate,
        LocalDateTime updateDate
) {
    @Builder(toBuilder = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ClientApiAddressDto(
            UUID id,
            String cep,
            Integer enderecoNumero,
            String enderecoComplemento,
            String enderecoRua,
            String enderecoBairro,
            String enderecoCidade,
            String enderecoUf,
            LocalDateTime creationDate,
            LocalDateTime updateDate
    ) {
    }
}
