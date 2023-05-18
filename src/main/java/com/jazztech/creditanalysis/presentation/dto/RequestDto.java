package com.jazztech.creditanalysis.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RequestDto(
        UUID clientId,
        Double monthlyIncome,
        Double requestedAmount
) {
}
