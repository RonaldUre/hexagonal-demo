package com.example.hexagonal.app.demo.customer.domain.model.vo;

import java.util.Objects;

/**
 * Value Object for Customer identifier.
 * Immutable and validated: must be > 0.
 */
public record CustomerId(Long value) {

    public CustomerId {
        Objects.requireNonNull(value, "CustomerId must not be null");
        if (value <= 0) {
            throw new IllegalArgumentException("CustomerId must be positive");
        }
    }

    public static CustomerId of(Long value) {
        return new CustomerId(value);
    }

    public long asLong() {
        return value;
    }
}
 