package com.fintech.interaction.service.impl;

import com.fintech.interaction.dto.request.ConvertCurrenciesRq;
import com.fintech.interaction.dto.response.ConvertCurrencyRs;
import com.fintech.interaction.service.CurrencyService;
import com.fintech.interaction.service.FormattingService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConvertServiceImplTest {

    private final CurrencyService currencyService =  Mockito.mock(CurrencyService.class);

    private final FormattingService formattingService = Mockito.mock(FormattingServiceImpl.class);

    private final ConvertServiceImpl convertService = new ConvertServiceImpl(currencyService, formattingService);

    @Test
    void getConvertCurrencyRs() {
        when(currencyService.getRatesByCode("USD")).thenReturn(96.1079);
        when(currencyService.getRatesByCode("AZN")).thenReturn(56.5341);
        Double amount = (96.1079/56.5341)*10.0;
        when(formattingService.formatting(amount)).thenReturn(17.0);
        ConvertCurrenciesRq currenciesRq = ConvertCurrenciesRq.builder()
                .fromCurrency("USD")
                .toCurrency("AZN")
                .amount(10.0)
                .build();

        ConvertCurrencyRs result = convertService.getConvertCurrencyRs(currenciesRq);
        ConvertCurrencyRs expected = ConvertCurrencyRs.builder()
                .fromCurrency("USD")
                .toCurrency("AZN")
                .convertedAmount(17.0)
                .build();

        assertEquals(expected, result);
        verify(formattingService,times(1)).formatting(amount);
    }

    @Test
    void convertAmountUSDtoAZN() {
        when(currencyService.getRatesByCode("USD")).thenReturn(96.1079);
        when(currencyService.getRatesByCode("AZN")).thenReturn(56.5341);
        Double amount = (96.1079/56.5341)*10.0;
        when(formattingService.formatting(amount)).thenReturn(17.0);
        ConvertCurrenciesRq currenciesRq = ConvertCurrenciesRq.builder()
                .fromCurrency("USD")
                .toCurrency("AZN")
                .amount(10.0)
                .build();

        Double result = convertService.convertAmount(currenciesRq);

        assertEquals(17.0, result);
        verify(formattingService,times(1)).formatting(amount);
        verify(currencyService, times(1)).getRatesByCode("USD");
        verify(currencyService, times(1)).getRatesByCode("AZN");
    }

    @Test
    void convertAmountUSDtoRUB() {
        when(currencyService.getRatesByCode("USD")).thenReturn(96.1079);
        Double amount = 96.1079*10.0;
        when(formattingService.formatting(amount)).thenReturn(969.5);
        ConvertCurrenciesRq currenciesRq = ConvertCurrenciesRq.builder()
                .fromCurrency("USD")
                .toCurrency("RUB")
                .amount(10.0)
                .build();

        Double result = convertService.convertAmount(currenciesRq);

        assertEquals(969.5, result);
        verify(formattingService,times(1)).formatting(amount);
        verify(currencyService, times(1)).getRatesByCode("USD");
    }

    @Test
    void convertAmountRUBtoUSD() {
        when(currencyService.getRatesByCode("USD")).thenReturn(96.1079);
        Double amount = (1/96.1079)*10.0;
        when(formattingService.formatting(amount)).thenReturn(0.1);
        ConvertCurrenciesRq currenciesRq = ConvertCurrenciesRq.builder()
                .fromCurrency("RUB")
                .toCurrency("USD")
                .amount(10.0)
                .build();

        Double result = convertService.convertAmount(currenciesRq);

        assertEquals(0.1, result);
        verify(formattingService,times(1)).formatting(amount);
        verify(currencyService, times(1)).getRatesByCode("USD");
    }

    @Test
    void getCrossRate() {
        assertEquals(1.7142857142857144, convertService.getCrossRate(1.2, 0.7));
    }
}