package com.fintech.elk.controller;


import com.fintech.elk.service.ElkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/elk")
public class ElkController {

    private final ElkService elkService;

    @GetMapping("/info")
    public String getInfo() {
        log.info("Info test log before MDC " + LocalDateTime.now().toString());
        var requestId = UUID.randomUUID().toString();
        MDC.putCloseable("RequestId", requestId);
        String result = elkService.getLevel("INFO");
        log.info("Info test log " + LocalDateTime.now().toString());
        return result;
    }

    @GetMapping("/error")
    public String getError() {
        var requestId = UUID.randomUUID().toString();
        MDC.putCloseable("RequestId", requestId);
        String result = elkService.getLevel("ERROR");
        log.error("Error test log " + LocalDateTime.now().toString());
        return result;
    }

    @GetMapping("/warn")
    public String getWarn() {
        var requestId = UUID.randomUUID().toString();
        MDC.putCloseable("RequestId", requestId);
        String result = elkService.getLevel("WARN");
        log.warn("Warn test log " + LocalDateTime.now().toString());
        return result;
    }
}
