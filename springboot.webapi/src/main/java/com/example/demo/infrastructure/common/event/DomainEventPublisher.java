package com.example.demo.infrastructure.common.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisher implements EventPublisher{
    @Autowired
    private ApplicationEventPublisher eventBus;

    public void publish(Object event) {
        eventBus.publishEvent(event);
    }
}