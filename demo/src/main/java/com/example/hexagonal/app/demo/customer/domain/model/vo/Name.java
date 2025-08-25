package com.example.hexagonal.app.demo.customer.domain.model.vo;

import java.util.Objects;

/**
 * Value Object for Customer name.
 */
public record Name(String value) {

    private static final int MAX_LENGTH = 255;

    public Name {
        Objects.requireNonNull(value, "Name must not be null");

        String normalized = value.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (normalized.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Name length must be <= " + MAX_LENGTH);
        }

        value = normalized;
    }

    public static Name of(String raw) {
        return new Name(raw);
    }

    public String asString() {
        return value;
    }
}
