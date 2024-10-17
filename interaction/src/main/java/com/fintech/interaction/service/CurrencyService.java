package com.fintech.interaction.service;

import com.fintech.interaction.dto.response.RatesRs;

public interface CurrencyService {

    RatesRs getRatesRsByCode(String code);

    Double getRatesByCode(String code);
}