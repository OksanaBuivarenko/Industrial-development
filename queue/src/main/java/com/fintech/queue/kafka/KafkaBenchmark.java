package com.fintech.queue.kafka;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@State(Scope.Benchmark)
public class KafkaBenchmark {

    private final List<KafkaProducerService> kafkaProducerList = new ArrayList<>();

    private final List<KafkaListener> kafkaListenerList = new ArrayList<>();

    private String message = "Kafka message";

    public KafkaBenchmark() {
        for (int i = 0; i < 10; i++) {
            kafkaProducerList.add(new KafkaProducerService());
            kafkaListenerList.add(new KafkaListener());
        }
    }

    @Benchmark
    public void simpleKafka() {
        kafkaProducerList.get(0).send(message);
        kafkaListenerList.get(0).listen();
    }

    @Benchmark
    public void loadBalancingKafka() {
        for (int i = 0; i < 3; i++) {
            kafkaProducerList.get(i).send(message);
        }
        kafkaListenerList.get(0).listen();
    }

    @Benchmark
    public void multipleConsumersKafka() {
        kafkaProducerList.get(0).send(message);
        for (int i = 0; i < 3; i++) {
            kafkaListenerList.get(i).listen();
        }
    }

    @Benchmark
    public void loadBalancingPlusMultipleConsumersKafka() {
        for (int i = 0; i < 3; i++) {
            kafkaProducerList.get(i).send(message);
            kafkaListenerList.get(i).listen();
        }
    }

    @Benchmark
    public void stressTestKafka() {
        for (int i = 0; i < 10; i++) {
            kafkaProducerList.get(i).send(message);
            kafkaListenerList.get(i).listen();
        }
    }
}
