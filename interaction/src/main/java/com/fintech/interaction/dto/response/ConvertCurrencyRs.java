package com.fintech.interaction.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ConvertCurrencyRs {

    @Schema(example = "USD")
    private String fromCurrency;

    @Schema(example = "RUB")
    private String toCurrency;

    @Schema(example = "9000.5")
    private Double convertedAmount;
}