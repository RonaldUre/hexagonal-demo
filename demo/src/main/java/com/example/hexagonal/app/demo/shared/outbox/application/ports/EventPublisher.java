package com.example.hexagonal.app.demo.shared.outbox.application.ports;

import com.example.hexagonal.app.demo.shared.outbox.domain.DomainEvent;
import java.util.Collection;

public interface EventPublisher {
    void publish(DomainEvent event);

    default void publishAll(Collection<? extends DomainEvent> events) {
        if (events == null || events.isEmpty()) return;
        events.forEach(this::publish);
    }
}
