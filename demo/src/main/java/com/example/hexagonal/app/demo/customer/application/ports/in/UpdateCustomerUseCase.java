package com.example.hexagonal.app.demo.customer.application.ports.in;

import com.example.hexagonal.app.demo.customer.application.command.UpdateCustomerCommand;
import com.example.hexagonal.app.demo.customer.domain.Customer;

public interface UpdateCustomerUseCase {
    Customer execute(UpdateCustomerCommand command);
}