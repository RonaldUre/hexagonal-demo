package com.example.hexagonal.app.demo.customer.application.service;

import com.example.hexagonal.app.demo.customer.application.command.UpdateCustomerCommand;
import com.example.hexagonal.app.demo.customer.application.errors.CustomerNotFoundException;
import com.example.hexagonal.app.demo.customer.application.errors.EmailAlreadyInUseException;
import com.example.hexagonal.app.demo.customer.application.ports.out.CustomerRepository;
import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Email;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Name;

import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case: update customer's name and email in a single operation.
 * Loads aggregate, validates uniqueness if email changes, applies domain behavior, persists.
 */
import com.example.hexagonal.app.demo.customer.application.ports.in.UpdateCustomerUseCase;

@Transactional
public class UpdateCustomerService implements UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public UpdateCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    public Customer execute(UpdateCustomerCommand command) {
        CustomerId id = new CustomerId(command.customerId());
        Name newName = Name.of(command.newName());
        Email newEmail = Email.of(command.newEmail());

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id.asLong()));

        // Check if email is changing and if new email is available
        boolean emailChanged = !customer.email().asString().equals(newEmail.asString());
        if (emailChanged && customerRepository.existsByEmail(newEmail)) {
            throw new EmailAlreadyInUseException(newEmail.asString());
        }

        // Apply domain behavior - update both name and email
        customer.changeName(newName);
        if (emailChanged) {
            customer.changeEmail(newEmail);
        }

        return customerRepository.save(customer);
    }
}