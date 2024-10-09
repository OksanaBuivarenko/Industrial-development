package com.fintech.interaction.service.impl;

import com.fintech.interaction.dto.request.ConvertCurrenciesRq;
import com.fintech.interaction.dto.response.ConvertCurrencyRs;
import com.fintech.interaction.service.ConvertService;
import com.fintech.interaction.service.CurrencyService;
import com.fintech.interaction.service.FormattingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConvertServiceImpl implements ConvertService {

    private final CurrencyService currencyService;

    private final FormattingService<Double, Double> formattingService;

    @Override
    public ConvertCurrencyRs getConvertCurrencyRs(ConvertCurrenciesRq convertRq) {
        return ConvertCurrencyRs.builder()
                .fromCurrency(convertRq.getFromCurrency())
                .toCurrency(convertRq.getToCurrency())
                .convertedAmount(convertAmount(convertRq))
                .build();
    }

    public Double convertAmount(ConvertCurrenciesRq convertRq) {
        double fromValuteRate;
        double toValuteRate;
        double convertAmount;
        if (convertRq.getFromCurrency().equals("RUB")) {
            toValuteRate = currencyService.getValuteByCode(convertRq.getToCurrency()).getVunitRate();
            convertAmount = getRateRUBtoCurrency(toValuteRate) * convertRq.getAmount();
        } else if (convertRq.getToCurrency().equals("RUB")) {
            fromValuteRate = currencyService.getValuteByCode(convertRq.getFromCurrency()).getVunitRate();
            convertAmount = fromValuteRate * convertRq.getAmount();
        } else {
            fromValuteRate = currencyService.getValuteByCode(convertRq.getFromCurrency()).getVunitRate();
            toValuteRate = currencyService.getValuteByCode(convertRq.getToCurrency()).getVunitRate();
            convertAmount = getCrossRate(fromValuteRate, toValuteRate) * convertRq.getAmount();
        }
        return formattingService.formatting(convertAmount);
    }

    public Double getCrossRate(Double from, Double to) {
        return from / to;
    }

    public Double getRateRUBtoCurrency(Double to) {
        return 1 / to;
    }
}