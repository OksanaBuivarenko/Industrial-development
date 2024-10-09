package com.fintech.interaction.service.impl;

import com.fintech.interaction.annotation.ExistingCurrency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Currency;

public class ExistingCurrencyValidator implements ConstraintValidator<ExistingCurrency, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.isEmpty()) {
            try {
                Currency.getInstance(value);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}