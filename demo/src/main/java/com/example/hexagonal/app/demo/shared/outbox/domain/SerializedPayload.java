package com.example.hexagonal.app.demo.shared.outbox.domain;

import java.util.Objects;

public record SerializedPayload(String json) {
    public SerializedPayload {
        Objects.requireNonNull(json, "payload must not be null");
        String s = json.trim();
        if (s.isEmpty()) throw new IllegalArgumentException("payload must not be blank");
        json = s;
    }
    public static SerializedPayload of(String json) { return new SerializedPayload(json); }
    public String asString() { return json; }
}
