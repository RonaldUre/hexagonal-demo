package com.example.hexagonal.app.demo.shared.outbox.domain;

import java.util.Objects;

public record AggregateRef(String aggregateType, Long aggregateId) {
    private static final int MAX_TYPE = 100;

    public AggregateRef {
        Objects.requireNonNull(aggregateType, "aggregateType must not be null");
        Objects.requireNonNull(aggregateId, "aggregateId must not be null");

        String t = aggregateType.trim();
        if (t.isEmpty()) throw new IllegalArgumentException("aggregateType must not be blank");
        if (t.length() > MAX_TYPE) throw new IllegalArgumentException("aggregateType length > " + MAX_TYPE);
        if (aggregateId <= 0) throw new IllegalArgumentException("aggregateId must be positive");

        aggregateType = t;
    }

    public static AggregateRef of(String type, Long id) { return new AggregateRef(type, id); }
}