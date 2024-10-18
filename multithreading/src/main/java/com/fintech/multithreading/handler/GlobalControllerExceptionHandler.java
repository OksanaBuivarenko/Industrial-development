package com.fintech.multithreading.handler;

import com.fintech.multithreading.dto.response.ErrorRs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRs> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> params = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            params.put(fieldName, errorMessage);
        });
        String error = "Not valid fields";
        log.error(error);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorRs.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .error(error)
                        .params(params).build());
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorRs> handleServiceUnavailableException(WebClientResponseException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("Retry-After", "3600")
                .body(ErrorRs.builder()
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorRs> handleServiceUnavailableException(RestClientException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("Retry-After", "3600")
                .body(ErrorRs.builder()
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .error(ex.getMessage())
                        .build());
    }
}