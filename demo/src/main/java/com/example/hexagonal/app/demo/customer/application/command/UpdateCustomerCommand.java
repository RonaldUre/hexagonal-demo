package com.example.hexagonal.app.demo.customer.application.command;

import java.util.Objects;

/** Command: update customer's name and email in a single operation. */
public record UpdateCustomerCommand(Long customerId, String newName, String newEmail) {
    public UpdateCustomerCommand {
        Objects.requireNonNull(customerId, "customerId must not be null");
        Objects.requireNonNull(newName, "newName must not be null");
        Objects.requireNonNull(newEmail, "newEmail must not be null");
    }
}