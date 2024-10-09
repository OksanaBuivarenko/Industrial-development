package com.fintech.interaction.controller;

import com.fintech.interaction.dto.request.ConvertCurrenciesRq;
import com.fintech.interaction.dto.response.ConvertCurrencyRs;
import com.fintech.interaction.dto.response.ErrorRs;
import com.fintech.interaction.dto.response.RatesRs;
import com.fintech.interaction.service.ConvertService;
import com.fintech.interaction.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currencies")
public class CurrenciesController {

    private final CurrencyService currencyService;

    private final ConvertService convertService;

    @Operation(description = "Getting the currency rate by code for the current day")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request",
                    content = @Content(schema = @Schema(implementation = RatesRs.class))),
            @ApiResponse(responseCode = "404", description = "Currency with this code was not found in the Central Bank",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class)))})
    @GetMapping("/rates/{code}")
    public RatesRs getRatesByCode(@PathVariable @Parameter(description = "Currency code",
            example = "USD") String code) {
        return currencyService.getRatesByCode(code);
    }

    @Operation(description = "Currency conversion at the current rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request",
                    content = @Content(schema = @Schema(implementation = ConvertCurrencyRs.class))),
            @ApiResponse(responseCode = "400", description = "Field is not valid",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class))),
            @ApiResponse(responseCode = "404", description = "Currency with this code was not found in the Central Bank",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class)))
    })
    @PostMapping("/convert")
    public ConvertCurrencyRs convertCurrencies(@Valid @RequestBody ConvertCurrenciesRq convertRq) {
        return convertService.getConvertCurrencyRs(convertRq);
    }
}