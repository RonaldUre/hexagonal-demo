package com.example.hexagonal.app.demo.shared.outbox.domain;

import java.time.Instant;
import java.util.Objects;

/**
 * Aggregate Root for table 'outbox'.
 * Campos: id, aggregate_type, aggregate_id, type, payload, created_at, processed.
 */
public final class OutboxMessage {

    private OutboxId id;                       // null hasta persistir
    private final AggregateRef aggregateRef;   // type + id
    private final EventType eventType;         // logical event name
    private final SerializedPayload payload;   // JSON
    private final Instant createdAt;           // set en creación
    private boolean processed;                 // default false

    private OutboxMessage(OutboxId id,
                          AggregateRef aggregateRef,
                          EventType eventType,
                          SerializedPayload payload,
                          Instant createdAt,
                          boolean processed) {
        this.id = id;
        this.aggregateRef = Objects.requireNonNull(aggregateRef);
        this.eventType = Objects.requireNonNull(eventType);
        this.payload = Objects.requireNonNull(payload);
        this.createdAt = Objects.requireNonNullElseGet(createdAt, Instant::now);
        this.processed = processed;
    }

    /** Crear nuevo registro (aún sin id). */
    public static OutboxMessage create(AggregateRef ref, EventType type, SerializedPayload payload) {
        return new OutboxMessage(null, ref, type, payload, Instant.now(), false);
    }

    /** Rehidratación desde persistencia. */
    public static OutboxMessage rehydrate(OutboxId id, AggregateRef ref, EventType type,
                                          SerializedPayload payload, Instant createdAt, boolean processed) {
        return new OutboxMessage(id, ref, type, payload, createdAt, processed);
    }

    /** Asignar id generado por BD (idempotente). */
    public void assignId(OutboxId id) {
        if (this.id != null) return;
        this.id = Objects.requireNonNull(id);
    }

    /** Marcar como procesado (idempotente). */
    public void markProcessed() { this.processed = true; }

    // Getters
    public OutboxId id() { return id; }
    public AggregateRef aggregateRef() { return aggregateRef; }
    public EventType eventType() { return eventType; }
    public SerializedPayload payload() { return payload; }
    public Instant createdAt() { return createdAt; }
    public boolean processed() { return processed; }
}
