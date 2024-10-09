package com.fintech.interaction.dto.request;

import com.fintech.interaction.annotation.ExistingCurrency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConvertCurrenciesRq {

    @Schema(example = "USD")
    @ExistingCurrency
    @NotBlank
    private String fromCurrency;

    @Schema(example = "RUB")
    @NotBlank
    private String toCurrency;

    @Schema(example = "100.5")
    @Positive
    @NotNull
    private Double amount;
}