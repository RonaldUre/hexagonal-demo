package com.example.hexagonal.app.demo.customer.application.ports.in;

import com.example.hexagonal.app.demo.customer.application.command.RegisterCustomerCommand;
import com.example.hexagonal.app.demo.customer.domain.Customer;

public interface RegisterCustomerUseCase {
    Customer execute(RegisterCustomerCommand command);
}