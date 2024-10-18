package com.fintech.interaction.annotation;

import com.fintech.interaction.service.impl.ExistingCurrencyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ExistingCurrencyValidator.class)
@Documented
public @interface ExistingCurrency {

    String message() default "must contain an existing currency code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}