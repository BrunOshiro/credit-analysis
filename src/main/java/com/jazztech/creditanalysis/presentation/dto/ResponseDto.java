package com.jazztech.creditanalysis.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDto(
        UUID id,
        Boolean approved,
        Double approvedLimit,
        Double withdraw,
        Double annualInterest,
        UUID clientId,
        LocalDateTime date
) {
}
