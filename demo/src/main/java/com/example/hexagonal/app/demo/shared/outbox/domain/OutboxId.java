package com.example.hexagonal.app.demo.shared.outbox.domain;

import java.util.Objects;

public record OutboxId(Long value) {
    public OutboxId {
        Objects.requireNonNull(value, "OutboxId must not be null");
        if (value <= 0) throw new IllegalArgumentException("OutboxId must be positive");
    }
    public static OutboxId of(Long value) { return new OutboxId(value); }
    public long asLong() { return value; }
}
