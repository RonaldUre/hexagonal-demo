package com.example.hexagonal.app.demo.customer.application.service;

import com.example.hexagonal.app.demo.customer.application.command.RegisterCustomerCommand;
import com.example.hexagonal.app.demo.customer.application.errors.EmailAlreadyInUseException;
import com.example.hexagonal.app.demo.customer.application.ports.out.CustomerRepository;
import com.example.hexagonal.app.demo.customer.application.ports.in.RegisterCustomerUseCase;
import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Email;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Name;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Use case: register a new Customer.
 * Orchestrates VO creation, uniqueness check and persistence.
 */
@Transactional
public class RegisterCustomerService implements RegisterCustomerUseCase {

    private final CustomerRepository customerRepository;

    public RegisterCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    public Customer execute(RegisterCustomerCommand command) {
        Name name = Name.of(command.name());
        Email email = Email.of(command.email());

        if (customerRepository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException(email.asString());
        }

        Customer customer = Customer.register(name, email);
        return customerRepository.save(customer);
    }
}
