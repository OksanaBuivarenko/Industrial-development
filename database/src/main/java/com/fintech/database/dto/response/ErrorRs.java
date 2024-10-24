package com.fintech.database.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ErrorRs {

    private HttpStatus status;

    private String error;
}