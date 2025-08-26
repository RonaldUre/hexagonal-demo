package com.example.hexagonal.app.demo.customer.application.ports.in;

import com.example.hexagonal.app.demo.customer.application.query.GetCustomerByIdQuery;
import com.example.hexagonal.app.demo.customer.domain.Customer;

public interface GetCustomerByIdUseCase {
    Customer handle(GetCustomerByIdQuery query);
}