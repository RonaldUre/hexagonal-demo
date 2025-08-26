package com.example.hexagonal.app.demo.customer.application.query;

import com.example.hexagonal.app.demo.customer.application.ports.out.CustomerRepository;

import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

import com.example.hexagonal.app.demo.customer.application.ports.in.CheckEmailAvailabilityUseCase;

@Transactional(readOnly = true)
public class CheckEmailAvailabilityHandler implements CheckEmailAvailabilityUseCase {

    private final CustomerRepository repository;

    public CheckEmailAvailabilityHandler(CustomerRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public boolean handle(CheckEmailAvailabilityQuery query) {
        return !repository.existsByEmail(query.toEmail());
    }
}
