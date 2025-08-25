package com.example.hexagonal.app.demo.customer.application.command;

import java.util.Objects;

/** Command: change the customer's name. */
public record UpdateCustomerNameCommand(Long customerId, String newName) {
    public UpdateCustomerNameCommand {
        Objects.requireNonNull(customerId, "customerId must not be null");
        Objects.requireNonNull(newName, "newName must not be null");
    }
}
