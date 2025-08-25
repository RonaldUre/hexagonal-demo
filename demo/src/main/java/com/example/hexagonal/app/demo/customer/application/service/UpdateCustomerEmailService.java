package com.example.hexagonal.app.demo.customer.application.service;

import com.example.hexagonal.app.demo.customer.application.command.UpdateCustomerEmailCommand;
import com.example.hexagonal.app.demo.customer.application.ports.CustomerRepository;
import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Email;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Use case: change customer's email.
 * Enforces uniqueness via repository before persisting.
 *
 * NOTE: Requires a domain method in AR: Customer.changeEmail(Email newEmail).
 */
public final class UpdateCustomerEmailService {

    private final CustomerRepository customerRepository;

    public UpdateCustomerEmailService(CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    public Customer execute(UpdateCustomerEmailCommand command) {
        CustomerId id = new CustomerId(command.customerId());
        Email newEmail = Email.of(command.newEmail());

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found: " + id.asLong()));

        // If email is unchanged, short-circuit
        if (customer.email().asString().equals(newEmail.asString())) {
            return customer; // no-op
        }

        if (customerRepository.existsByEmail(newEmail)) {
            throw new IllegalStateException("Email already in use: " + newEmail.asString());
        }

        // Requires AR behavior; we lo keep explicit in the domain
        customer.changeEmail(newEmail);

        return customerRepository.save(customer);
    }
}
