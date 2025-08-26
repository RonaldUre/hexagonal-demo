package com.example.hexagonal.app.demo.customer.application.ports.in;

import com.example.hexagonal.app.demo.customer.application.query.ListCustomersQuery;
import com.example.hexagonal.app.demo.customer.domain.Customer;

import java.util.List;

public interface ListCustomersUseCase {
    List<Customer> handle(ListCustomersQuery query);
}