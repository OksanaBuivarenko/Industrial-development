package com.fintech.multithreading.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyDto {

    private String fromCurrency;

    private String toCurrency;

    private Double amount;
}
