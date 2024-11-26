package com.fintech.elk.controller;

import com.fintech.elk.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/metric")
public class MetricsController {

    private final DictionaryService dictionary;

    @PostMapping()
    public String addWord() {
        dictionary.addWord("Word");
        return "New word added to the dictionary";
    }
}
