package com.jazztech.infrastructure;

import com.jazztech.creditanalysis.infrastructure.clientsapi.dto.ClientApiDto;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Factory {
    public static RequestDto requestDtoFactory() {
        return RequestDto.builder()
                .clientId(UUID.fromString("dd1efc87-a9c1-4f08-8fb7-1cd71d92dd6d"))
                .monthlyIncome(BigDecimal.valueOf(10000.00))
                .requestedAmount(BigDecimal.valueOf(5000.00))
                .build();
    }

    public static ResponseDto responseDto() {
        return ResponseDto.builder()
                .id(UUID.fromString("ef89557d-7a96-4044-905d-046652e2af95"))
                .approved(true)
                .approvedLimit(BigDecimal.valueOf(3000.00))
                .withdraw(BigDecimal.valueOf(300.00))
                .annualInterest(BigDecimal.valueOf(0.15))
                .clientId(UUID.fromString("dd1efc87-a9c1-4f08-8fb7-1cd71d92dd6d"))
                .date(LocalDateTime.now())
                .build();
    }

    public static ClientApiDto clientApiDtoFactory() {
        return ClientApiDto.builder()
                .id(UUID.fromString("dd1efc87-a9c1-4f08-8fb7-1cd71d92dd6d"))
                .nome("Lauren Ipson")
                .cpf("18784272023")
                .dataNascimento(LocalDate.parse("1993-01-01"))
                .clientApiAddressDto(ClientApiDto.ClientApiAddressDto.builder()
                        .id(UUID.fromString("3b92e416-898c-4379-9b78-14ff661c755a"))
                        .cep("84072-140")
                        .enderecoNumero(51)
                        .enderecoComplemento("there is a stone")
                        .enderecoRua("Rua Lydia Henneberg Fanchin")
                        .enderecoBairro("Boa Vista")
                        .enderecoCidade("Ponta Grossa")
                        .enderecoUf("PR")
                        .build())
                .build();
    }
}
