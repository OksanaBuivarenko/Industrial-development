package com.fintech.interaction.service.impl;

import com.fintech.interaction.dto.request.ConvertCurrenciesRq;
import com.fintech.interaction.dto.response.ConvertCurrencyRs;
import com.fintech.interaction.model.Valute;
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

    private Valute usd;

    private Valute azn;

    @BeforeEach
    public  void setUp() {
        usd = new Valute("1", "1", "USD", "n", "n","1", 96.1079);
        azn = new Valute("1", "1", "USD", "n", "n","1", 56.5341);
    }

    @AfterEach
    public void tearDown() {
        usd = null;
        azn = null;
    }

    @Test
    void getConvertCurrencyRs() {
        when(currencyService.getValuteByCode("USD")).thenReturn(usd);
        when(currencyService.getValuteByCode("AZN")).thenReturn(azn);
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
        when(currencyService.getValuteByCode("USD")).thenReturn(usd);
        when(currencyService.getValuteByCode("AZN")).thenReturn(azn);
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
        verify(currencyService, times(1)).getValuteByCode("USD");
        verify(currencyService, times(1)).getValuteByCode("AZN");
    }

    @Test
    void convertAmountUSDtoRUB() {
        when(currencyService.getValuteByCode("USD")).thenReturn(usd);
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
        verify(currencyService, times(1)).getValuteByCode("USD");
    }

    @Test
    void convertAmountRUBtoUSD() {
        when(currencyService.getValuteByCode("USD")).thenReturn(usd);
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
        verify(currencyService, times(1)).getValuteByCode("USD");
    }

    @Test
    void getCrossRate() {
        assertEquals(1.7142857142857144, convertService.getCrossRate(1.2, 0.7));
    }
}