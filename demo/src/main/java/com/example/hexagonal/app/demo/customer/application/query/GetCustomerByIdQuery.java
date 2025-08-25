package com.example.hexagonal.app.demo.customer.application.query;

import java.util.Objects;

public record GetCustomerByIdQuery(Long customerId) {
    public GetCustomerByIdQuery {
        Objects.requireNonNull(customerId, "customerId must not be null");
    }
}
