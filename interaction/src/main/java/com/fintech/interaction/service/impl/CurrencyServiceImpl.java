package com.fintech.interaction.service.impl;

import com.fintech.interaction.dto.response.RatesRs;
import com.fintech.interaction.exception.ValuteNotFoundException;
import com.fintech.interaction.mapper.ValuteMapper;
import com.fintech.interaction.model.Valute;
import com.fintech.interaction.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final ExchangeRateService exchangeRateService;

    private final ValuteMapper valuteMapper;

    @Override
    public RatesRs getRatesByCode(String code) {
        return valuteMapper.toDto(getValuteByCode(code));
    }

    @Cacheable(cacheNames = "cache", cacheManager = "cacheManager", sync = true)
    @Override
    public Valute getValuteByCode(String code) {
        log.info("Call method getValuteByCode with code - " + code);
        return exchangeRateService.getListByApi().stream().filter(valute -> valute.getCharCode().equals(code))
                .findFirst().orElseThrow(() -> new ValuteNotFoundException("Currency with code "
                        + code + " was not found in the Central Bank"));
    }
}