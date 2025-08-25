package com.example.hexagonal.app.demo.customer.infrastructure.web.dto;

import com.example.hexagonal.app.demo.customer.domain.Customer;

import java.time.Instant;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        Instant createdAt
) {
    public static CustomerResponse fromDomain(Customer customer) {
        return new CustomerResponse(
                customer.id().asLong(),
                customer.name().asString(),
                customer.email().asString(),
                customer.createdAt()
        );
    }
}