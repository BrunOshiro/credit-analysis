package com.jazztech.creditanalysis.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDto(
        UUID id,
        Boolean approved,
        Double approvedLimit,
        Double withdraw,
        Double annualInterest,
        UUID clientId,
        LocalDate date
) {
}
