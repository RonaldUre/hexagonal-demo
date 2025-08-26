package com.example.hexagonal.app.demo.customer.application.query;

import com.example.hexagonal.app.demo.customer.application.ports.out.CustomerRepository;
import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;

import com.example.hexagonal.app.demo.customer.application.errors.CustomerNotFoundException;
import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

import com.example.hexagonal.app.demo.customer.application.ports.in.GetCustomerByIdUseCase;

@Transactional(readOnly = true)
public class GetCustomerByIdHandler implements GetCustomerByIdUseCase {

    private final CustomerRepository repository;

    public GetCustomerByIdHandler(CustomerRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public Customer handle(GetCustomerByIdQuery query) {
        CustomerId id = new CustomerId(query.customerId());
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id.asLong()));
    }
}
