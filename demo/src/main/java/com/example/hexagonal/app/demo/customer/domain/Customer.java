package com.example.hexagonal.app.demo.customer.domain;

import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Email;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Name;

import java.time.Instant;
import java.util.Objects;

/**
 * Aggregate Root for Customer.
 * Controls invariants. No domain events are emitted for CRUD-only flows.
 */
public final class Customer {

    private CustomerId id; // null until persisted
    private Email email;
    private Name name;
    private final Instant createdAt;

    private Customer(CustomerId id, Email email, Name name, Instant createdAt) {
        this.id = id;
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.createdAt = Objects.requireNonNullElseGet(createdAt, Instant::now);
    }

    /** Factory for creating a new customer (no id yet). */
    public static Customer register(Name name, Email email) {
        return new Customer(null, email, name, Instant.now());
    }

    public void changeName(Name newName) {
        this.name = Objects.requireNonNull(newName, "newName must not be null");
    }

    public void changeEmail(Email newEmail) {
        this.email = Objects.requireNonNull(newEmail, "newEmail must not be null");
    }

    /** Used by persistence adapter to attach generated id after save. */
    public void assignId(CustomerId id) {
        if (this.id != null) return; // idempotent
        this.id = Objects.requireNonNull(id, "id must not be null");
    }

    // Getters
    public CustomerId id() { return id; }
    public Email email() { return email; }
    public Name name() { return name; }
    public Instant createdAt() { return createdAt; }

    /** Rehydration factory for repository (from persistence) */
    public static Customer rehydrate(CustomerId id, Email email, Name name, Instant createdAt) {
        return new Customer(id, email, name, createdAt);
    }
}
