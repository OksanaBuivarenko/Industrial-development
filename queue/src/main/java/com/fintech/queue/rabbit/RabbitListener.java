package com.fintech.queue.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitListener {

    private final RabbitTemplate rabbitTemplate;

    public RabbitListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void receive() {
        rabbitTemplate.receiveAndConvert("queue1", String.class.getModifiers());
    }
}