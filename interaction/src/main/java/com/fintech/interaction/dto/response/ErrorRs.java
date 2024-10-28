package com.fintech.interaction.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorRs {

    @Schema(example = "503")
    private Integer code;

    @Schema(example = "Unsupported currency code")
    private String message;
}