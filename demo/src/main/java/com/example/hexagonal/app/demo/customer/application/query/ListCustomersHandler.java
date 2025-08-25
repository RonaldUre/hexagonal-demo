package com.example.hexagonal.app.demo.customer.application.query;

import com.example.hexagonal.app.demo.customer.application.ports.CustomerRepository;
import com.example.hexagonal.app.demo.customer.domain.Customer;

import java.util.List;
import java.util.Objects;

public class ListCustomersHandler {

    private final CustomerRepository repository;

    public ListCustomersHandler(CustomerRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public List<Customer> handle(ListCustomersQuery query) {
        return repository.findAll(query.page(), query.size());
    }
}
