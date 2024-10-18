package com.fintech.interaction.service.impl;

import com.fintech.interaction.dto.response.RatesRs;
import com.fintech.interaction.exception.ValuteNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrencyServiceImplTest {

    private final ExchangeRateService exchangeRateService = mock(ExchangeRateService.class);

    private final CurrencyServiceImpl currencyService = new CurrencyServiceImpl(exchangeRateService);

    private Map<String, Double> valutes;

    @BeforeEach
    public void setUp() {
        valutes = new HashMap<>();
        valutes.put("USD", 96.1079);
        valutes.put("AZN", 56.5341);
    }

    @AfterEach
    public void tearDown() {
        valutes = null;
    }

    @Test
    void getRatesRsByCode() {
        RatesRs ratesRs = RatesRs.builder().rate("96.1079").currency("USD").build();
        when(exchangeRateService.getMapByApi()).thenReturn(valutes);

        assertEquals(ratesRs, currencyService.getRatesRsByCode("USD"));
    }

    @Test
    void getRatesByIsPresentCodeSuccess() {
        when(exchangeRateService.getMapByApi()).thenReturn(valutes);

        Double result = currencyService.getRatesByCode("USD");

        assertEquals(96.1079, result);
        verify(exchangeRateService, times(1)).getMapByApi();
    }

    @Test
    void getRatesByIsNotPresentCodeFail() {
        when(exchangeRateService.getMapByApi()).thenReturn(valutes);

        Exception ex = assertThrows(ValuteNotFoundException.class,() -> currencyService.getRatesByCode("USDUSD"));
        assertEquals("Currency with code USDUSD was not found in the Central Bank", ex.getMessage());
    }
}