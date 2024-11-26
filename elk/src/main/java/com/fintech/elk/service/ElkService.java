package com.fintech.elk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class ElkService {

    public String getLevel(String level) {
        log.info("Called method with level " + level);
        return "Log's level is " + level;
    }
}
