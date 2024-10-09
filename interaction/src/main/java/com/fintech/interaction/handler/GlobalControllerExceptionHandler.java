package com.fintech.interaction.handler;

import com.fintech.interaction.dto.response.ErrorRs;
import com.fintech.interaction.exception.ValuteNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRs> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.append("Field ");
            errors.append(((FieldError) error).getField());
            errors.append(" ");
            errors.append(error.getDefaultMessage());
            errors.append(System.lineSeparator());
        });
        log.error(errors.toString());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorRs(HttpStatus.BAD_REQUEST.value(), errors.toString()));
    }

    @ExceptionHandler(ValuteNotFoundException.class)
    public ResponseEntity<ErrorRs> handleValuteNotFoundException(ValuteNotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorRs(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorRs> handleServiceUnavailableException(WebClientResponseException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("Retry-After", "3600")
                .body(new ErrorRs(HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage()));
    }
}