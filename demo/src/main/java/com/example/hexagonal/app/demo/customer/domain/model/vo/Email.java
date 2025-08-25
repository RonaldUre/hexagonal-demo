package com.example.hexagonal.app.demo.customer.domain.model.vo;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object for Email with normalization and validation.
 */
public record Email(String value) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );
    private static final int MAX_LENGTH = 255;

    public Email {
        Objects.requireNonNull(value, "Email must not be null");

        String normalized = value.trim().toLowerCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Email must not be blank");
        }
        if (normalized.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Email length must be <= " + MAX_LENGTH);
        }
        if (!EMAIL_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Email format is invalid");
        }

        value = normalized;
    }

    public static Email of(String raw) {
        return new Email(raw);
    }

    public String asString() {
        return value;
    }
}