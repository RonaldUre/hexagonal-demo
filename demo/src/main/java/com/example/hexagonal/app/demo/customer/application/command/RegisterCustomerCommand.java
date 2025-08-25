package com.example.hexagonal.app.demo.customer.application.command;

import java.util.Objects;

/** Command: register a new Customer. */
public record RegisterCustomerCommand(String name, String email) {
    public RegisterCustomerCommand {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(email, "email must not be null");
    }
}
