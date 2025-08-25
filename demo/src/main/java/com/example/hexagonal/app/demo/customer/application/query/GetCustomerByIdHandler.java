package com.example.hexagonal.app.demo.customer.application.query;

import com.example.hexagonal.app.demo.customer.application.ports.CustomerRepository;
import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;

import java.util.NoSuchElementException;
import java.util.Objects;

public class GetCustomerByIdHandler {

    private final CustomerRepository repository;

    public GetCustomerByIdHandler(CustomerRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public Customer handle(GetCustomerByIdQuery query) {
        CustomerId id = new CustomerId(query.customerId());
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found: " + id.asLong()));
    }
}
