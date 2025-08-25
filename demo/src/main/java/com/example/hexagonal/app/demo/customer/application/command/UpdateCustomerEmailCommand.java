package com.example.hexagonal.app.demo.customer.application.command;

import java.util.Objects;

/** Command: change the customer's email (will require uniqueness check in service). */
public record UpdateCustomerEmailCommand(Long customerId, String newEmail) {
    public UpdateCustomerEmailCommand {
        Objects.requireNonNull(customerId, "customerId must not be null");
        Objects.requireNonNull(newEmail, "newEmail must not be null");
    }
}
