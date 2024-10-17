package com.fintech.interaction.service.impl;

import com.fintech.interaction.dto.response.RatesRs;
import com.fintech.interaction.exception.ValuteNotFoundException;
import com.fintech.interaction.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final ExchangeRateService exchangeRateService;

    @Override
    public RatesRs getRatesRsByCode(String code) {
        return RatesRs.builder()
                .currency(code)
                .rate(getRatesByCode(code).toString())
                .build();
    }

    @Override
    public Double getRatesByCode(String code) {
        log.info("Call method getValuteByCode with code - " + code);
        Double result = exchangeRateService.getMapByApi().get(code);
        if (result != null) {
            return result;
        } else {
           throw new ValuteNotFoundException("Currency with code " + code + " was not found in the Central Bank");
        }
    }
}