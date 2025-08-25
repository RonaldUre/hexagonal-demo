package com.example.hexagonal.app.demo.shared.outbox.domain;

import java.util.Objects;

public record EventType(String value) {
    private static final int MAX = 100;

    public EventType {
        Objects.requireNonNull(value, "event type must not be null");
        String v = value.trim();
        if (v.isEmpty()) throw new IllegalArgumentException("event type must not be blank");
        if (v.length() > MAX) throw new IllegalArgumentException("event type length > " + MAX);
        value = v;
    }

    public static EventType of(String value) { return new EventType(value); }
    public String asString() { return value; }
}