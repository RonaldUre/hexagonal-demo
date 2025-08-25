package com.example.hexagonal.app.demo.customer.application.query;

import com.example.hexagonal.app.demo.customer.application.ports.CustomerRepository;

import java.util.Objects;

public class CheckEmailAvailabilityHandler {

    private final CustomerRepository repository;

    public CheckEmailAvailabilityHandler(CustomerRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public boolean handle(CheckEmailAvailabilityQuery query) {
        return !repository.existsByEmail(query.toEmail());
    }
}
