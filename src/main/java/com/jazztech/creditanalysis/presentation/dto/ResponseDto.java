package com.jazztech.creditanalysis.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDto(
        UUID id,
        Boolean approved,
        BigDecimal approvedLimit,
        BigDecimal withdraw,
        BigDecimal annualInterest,
        UUID clientId,
        LocalDateTime date
) {
}
