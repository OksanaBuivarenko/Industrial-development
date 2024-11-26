package com.fintech.elk.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
@Service
public class DictionaryService {

    private final List<String> words = new CopyOnWriteArrayList<>();

    DictionaryService() {
        MeterRegistry registry = Metrics.globalRegistry;
        registry.gaugeCollectionSize("dictionary_size", Tags.of("key", "value"), this.words);
    }

    public void addWord(String word) {
        words.add(word);
    }
}
