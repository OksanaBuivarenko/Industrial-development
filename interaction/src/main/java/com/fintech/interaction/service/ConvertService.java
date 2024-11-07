package com.fintech.interaction.service;

import com.fintech.interaction.dto.request.ConvertCurrenciesRq;
import com.fintech.interaction.dto.response.ConvertCurrencyRs;

public interface ConvertService {
    ConvertCurrencyRs getConvertCurrencyRs(ConvertCurrenciesRq convertRq);
}