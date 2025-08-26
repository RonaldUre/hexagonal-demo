package com.example.hexagonal.app.demo.customer.application.errors;

/** Thrown when attempting to register/change to an email that already exists. */
public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String email) {
        super("Email already in use: " + email);
    }
}