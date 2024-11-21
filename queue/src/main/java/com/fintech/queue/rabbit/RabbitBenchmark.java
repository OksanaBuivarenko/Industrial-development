package com.fintech.queue.rabbit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@State(Scope.Benchmark)
public class RabbitBenchmark {

    private RabbitTemplate rabbitTemplate;

    private final List<RabbitProducer> rabbitProducerList = new ArrayList<>();

    private final List<RabbitListener> rabbitListenerList = new ArrayList<>();

    private String message = "Rabbit message";

    public RabbitBenchmark() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
        rabbitTemplate = context.getBean(RabbitTemplate.class);
        for (int i = 0; i < 10; i++) {
            rabbitProducerList.add(new RabbitProducer(rabbitTemplate));
            rabbitListenerList.add(new RabbitListener(rabbitTemplate));
        }
    }

    @Benchmark
    public void simpleRabbit() {
        rabbitProducerList.get(0).send(message);
        rabbitListenerList.get(0).receive();
    }

    @Benchmark
    public void loadBalancingRabbit() {
        for (int i = 0; i < 3; i++) {
            rabbitProducerList.get(i).send(message);
        }
        rabbitListenerList.get(0).receive();
    }

    @Benchmark
    public void multipleConsumersRabbit() {
        rabbitProducerList.get(0).send(message);
        for (int i = 0; i < 3; i++) {
            rabbitListenerList.get(i).receive();
        }
    }

    @Benchmark
    public void loadBalancingPlusMultipleConsumersRabbit() {
        for (int i = 0; i < 3; i++) {
            rabbitProducerList.get(i).send(message);
            rabbitListenerList.get(i).receive();
        }
    }

    @Benchmark
    public void stressTestRabbit() {
        for (int i = 0; i < 10; i++) {
            rabbitProducerList.get(i).send(message);
            rabbitListenerList.get(i).receive();
        }
    }
}
