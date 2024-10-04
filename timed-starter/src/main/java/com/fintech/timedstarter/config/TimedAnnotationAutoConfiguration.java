package com.fintech.timedstarter.config;

import com.fintech.timedstarter.handler.TimedPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "starter", value = "enabled", havingValue = "true")
public class TimedAnnotationAutoConfiguration {

    @Bean
    public TimedPostProcessor timedPostProcessor() {
        return new TimedPostProcessor();
    }
}


