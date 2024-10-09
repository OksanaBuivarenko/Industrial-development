package com.fintech.interaction.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RatesRs {

    @Schema(example = "USD")
    private String currency;

    @Schema(example = "123,4")
    private String rate;
}