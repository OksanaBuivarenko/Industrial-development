package com.fintech.interaction.service;

import com.fintech.interaction.dto.response.RatesRs;
import com.fintech.interaction.model.Valute;

public interface CurrencyService {

    RatesRs getRatesByCode(String code);

    Valute getValuteByCode(String code);
}