package com.fintech.multithreading.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "semaphore", ignoreUnknownFields = false)
public class ApplicationConfig {

    @NotNull
    private Integer permits;
    @NotNull
    private Boolean fair;
}
