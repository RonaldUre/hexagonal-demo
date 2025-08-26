package com.example.hexagonal.app.demo.customer.application.service;

import com.example.hexagonal.app.demo.customer.application.command.UpdateCustomerNameCommand;
import com.example.hexagonal.app.demo.customer.application.errors.CustomerNotFoundException;
import com.example.hexagonal.app.demo.customer.application.ports.out.CustomerRepository;
import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Name;

import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case: change customer's name.
 * Loads aggregate, applies domain behavior, persists.
 */
import com.example.hexagonal.app.demo.customer.application.ports.in.UpdateCustomerNameUseCase;

@Transactional
public class UpdateCustomerNameService implements UpdateCustomerNameUseCase {

    private final CustomerRepository customerRepository;

    public UpdateCustomerNameService(CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    public Customer execute(UpdateCustomerNameCommand command) {
        CustomerId id = new CustomerId(command.customerId());
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id.asLong()));

        customer.changeName(Name.of(command.newName()));
        return customerRepository.save(customer);
    }
}
