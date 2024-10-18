package com.fintech.multithreading.service;

import com.fintech.multithreading.dto.CurrencyDto;

public interface CurrencyHttpService {

    Double getAmount(CurrencyDto currencyDto);
}
