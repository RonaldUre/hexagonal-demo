package com.example.hexagonal.app.demo.customer.application.ports.in;

import com.example.hexagonal.app.demo.customer.application.command.UpdateCustomerEmailCommand;
import com.example.hexagonal.app.demo.customer.domain.Customer;

public interface UpdateCustomerEmailUseCase {
    Customer execute(UpdateCustomerEmailCommand command);
}