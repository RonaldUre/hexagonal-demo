package com.example.hexagonal.app.demo.customer.application.query;

import com.example.hexagonal.app.demo.customer.application.ports.out.CustomerRepository;
import com.example.hexagonal.app.demo.customer.domain.Customer;

import java.util.List;
import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

import com.example.hexagonal.app.demo.customer.application.ports.in.ListCustomersUseCase;

@Transactional(readOnly = true)
public class ListCustomersHandler implements ListCustomersUseCase {

    private final CustomerRepository repository;

    public ListCustomersHandler(CustomerRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public List<Customer> handle(ListCustomersQuery query) {
        return repository.findAll(query.page(), query.size());
    }
}
