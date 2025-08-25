package com.example.hexagonal.app.demo.customer.application.service;

import com.example.hexagonal.app.demo.customer.application.command.UpdateCustomerNameCommand;
import com.example.hexagonal.app.demo.customer.application.ports.CustomerRepository;
import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Name;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Use case: change customer's name.
 * Loads aggregate, applies domain behavior, persists.
 */
public final class UpdateCustomerNameService {

    private final CustomerRepository customerRepository;

    public UpdateCustomerNameService(CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    public Customer execute(UpdateCustomerNameCommand command) {
        CustomerId id = new CustomerId(command.customerId());
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found: " + id.asLong()));

        customer.changeName(Name.of(command.newName()));
        return customerRepository.save(customer);
    }
}
