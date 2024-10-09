package com.fintech.interaction.exception;

public class ValuteNotFoundException extends RuntimeException {
    public ValuteNotFoundException(String message) {
        super(message);
    }
}