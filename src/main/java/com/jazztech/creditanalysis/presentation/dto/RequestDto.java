package com.jazztech.creditanalysis.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RequestDto(
        UUID clientId,
        BigDecimal monthlyIncome,
        BigDecimal requestedAmount
) {
}
