package com.example.hexagonal.app.demo.customer.application.service;

import com.example.hexagonal.app.demo.customer.application.command.UpdateCustomerCommand;
import com.example.hexagonal.app.demo.customer.application.ports.CustomerRepository;
import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Email;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Name;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Use case: update customer's name and email in a single operation.
 * Loads aggregate, validates uniqueness if email changes, applies domain behavior, persists.
 */
public final class UpdateCustomerService {

    private final CustomerRepository customerRepository;

    public UpdateCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    public Customer execute(UpdateCustomerCommand command) {
        CustomerId id = new CustomerId(command.customerId());
        Name newName = Name.of(command.newName());
        Email newEmail = Email.of(command.newEmail());

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found: " + id.asLong()));

        // Check if email is changing and if new email is available
        boolean emailChanged = !customer.email().asString().equals(newEmail.asString());
        if (emailChanged && customerRepository.existsByEmail(newEmail)) {
            throw new IllegalStateException("Email already in use: " + newEmail.asString());
        }

        // Apply domain behavior - update both name and email
        customer.changeName(newName);
        if (emailChanged) {
            customer.changeEmail(newEmail);
        }

        return customerRepository.save(customer);
    }
}