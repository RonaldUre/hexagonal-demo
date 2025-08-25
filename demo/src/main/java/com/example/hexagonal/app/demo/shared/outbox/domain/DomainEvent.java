package com.example.hexagonal.app.demo.shared.outbox.domain;

import java.time.Instant;

public interface DomainEvent {
    /** 
     * Nombre del evento, ej: "CustomerRegistered".
     * Útil para guardar en la columna type del outbox.
     */
    String eventType();

    /**
     * Momento en que ocurrió el evento (no cuándo se persistió).
     */
    Instant occurredOn();
}