package com.example.hexagonal.app.demo.shared.outbox.outbound.adapter;

import com.example.hexagonal.app.demo.shared.outbox.application.ports.EventPublisher;
import com.example.hexagonal.app.demo.shared.outbox.application.ports.OutboxRepository;
import com.example.hexagonal.app.demo.shared.outbox.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DomainEventPublisherAdapter implements EventPublisher {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public DomainEventPublisherAdapter(OutboxRepository outboxRepository,
                                       ObjectMapper objectMapper) {
        this.outboxRepository = Objects.requireNonNull(outboxRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public void publish(DomainEvent event) {
        if (!(event instanceof AggregateAwareEvent agg)) {
            // Requisito: los eventos publicados a outbox deben exponer aggregateType/id
            throw new IllegalArgumentException("Event must implement AggregateAwareEvent: " + event.getClass().getName());
        }
        Long aggregateId = agg.aggregateId();
        String aggregateType = agg.aggregateType();
        if (aggregateId == null || aggregateId <= 0) {
            // Por tu esquema, los NOT NULL deben ir completos
            throw new IllegalStateException("aggregateId must be a positive id before publishing to outbox");
        }
        String payloadJson = serialize(event);

        OutboxMessage msg = OutboxMessage.create(
                AggregateRef.of(aggregateType, aggregateId),
                EventType.of(event.eventType()),
                SerializedPayload.of(payloadJson)
        );

        outboxRepository.save(msg);
    }

    private String serialize(DomainEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize domain event: " + event.getClass().getSimpleName(), e);
        }
    }
}
