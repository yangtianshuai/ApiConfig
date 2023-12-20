package com.example.demo.infrastructure.common.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventPublisher implements EventPublisher{
    private final RabbitTemplate rabbitTemplate;
    public RabbitEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(Object event) {
        rabbitTemplate.convertAndSend(event);
    }
}
