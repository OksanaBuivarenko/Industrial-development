package com.fintech.interaction.service.impl;

import com.fintech.interaction.service.FormattingService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Service
public class FormattingServiceImpl implements FormattingService<Double, Double> {

    @Override
    public Double formatting(Double value) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.#", otherSymbols);
        return Double.valueOf(df.format(value));
    }
}