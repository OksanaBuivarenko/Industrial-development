package com.fintech.multithreading.dto.request;

import com.fintech.multithreading.enumeration.CurrencyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@Data
public class EventsRq {

    @Schema(description = "Бюджет пользователя")
    @NotNull
    private Double budget;

    @Schema(description = "Валюта пользователя")
    @NotNull
    private CurrencyType currency;

    @Schema(description = "Начало периода, за который пользователя интересуют события")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFrom;

    @Schema(description = "Конец периода, за который пользователя интересуют события")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTo;
}