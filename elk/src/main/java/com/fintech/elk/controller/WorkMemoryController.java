package com.fintech.elk.controller;

import com.fintech.elk.service.WorkMemoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memory")
public class WorkMemoryController {

    private final WorkMemoryService workMemoryService;

    @GetMapping("/stack")
    public String getStackOverflow() {
        workMemoryService.recursivePrint(1);
        return "StackOverflow";
    }

    @GetMapping("/out")
    public String getOutOfMemory() {
        workMemoryService.grabMemory();
        return "OutOfMemory";
    }
}
