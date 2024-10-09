package com.fintech.interaction.service.impl;

import com.fintech.interaction.dto.response.RatesRs;
import com.fintech.interaction.exception.ValuteNotFoundException;
import com.fintech.interaction.mapper.ValuteMapper;
import com.fintech.interaction.model.Valute;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrencyServiceImplTest {

    private final ExchangeRateService exchangeRateService = Mockito.mock(ExchangeRateService.class);

    private final ValuteMapper valuteMapper = Mockito.mock(ValuteMapper.class);

    private final CurrencyServiceImpl currencyService = new CurrencyServiceImpl(exchangeRateService, valuteMapper);

    private List<Valute> valutes;

    private Valute valute;

    @BeforeEach
    public void setUp() {
        valutes = new ArrayList<>();
        valute = new Valute("1", "1", "USD", "n", "n","1", 96.1079);
        valutes.add(valute);
        valutes.add(new Valute("2", "1", "AZN", "n", "n","1", 56.5341));
    }

    @AfterEach
    public void tearDown() {
        valutes = null;
        valute = null;
    }

    @Test
    void getRatesByCode() {
        RatesRs ratesRs = RatesRs.builder().rate("96.1079").currency("USD").build();
        when(valuteMapper.toDto(valute)).thenReturn(ratesRs);
        when(exchangeRateService.getListByApi()).thenReturn(valutes);

        assertEquals(ratesRs, currencyService.getRatesByCode("USD"));
    }

    @Test
    void getValuteByIsPresentCodeSuccess() {
        when(exchangeRateService.getListByApi()).thenReturn(valutes);

        Valute result = currencyService.getValuteByCode("USD");

        assertEquals("1", result.getId());
        verify(exchangeRateService, times(1)).getListByApi();
    }

    @Test
    void getValuteByIsNotPresentCodeFail() {
        when(exchangeRateService.getListByApi()).thenReturn(valutes);

        Exception ex = assertThrows(ValuteNotFoundException.class,() -> currencyService.getValuteByCode("USDUSD"));
        assertEquals("Currency with code USDUSD was not found in the Central Bank", ex.getMessage());
    }
}