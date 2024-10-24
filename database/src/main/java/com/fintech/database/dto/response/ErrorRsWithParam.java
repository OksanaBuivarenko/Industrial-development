package com.fintech.database.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
@Data
public class ErrorRsWithParam {

    private HttpStatus status;

    private String error;

    private Map<String, String> params;
}