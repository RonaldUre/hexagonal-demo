package com.example.hexagonal.app.demo.customer.application.errors;

/** Thrown when a customer is not found for a given id. */
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(long id) {
        super("Customer not found: " + id);
    }
}