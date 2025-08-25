package com.example.hexagonal.app.demo.shared.outbox.domain;

/** Events that expose the originating aggregate (type + id). */
public interface AggregateAwareEvent extends DomainEvent {
    String aggregateType();   // e.g., "Customer"
    Long aggregateId();       // e.g., 123L
}