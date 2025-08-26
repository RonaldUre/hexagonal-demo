package com.example.hexagonal.app.demo.customer.application.ports.in;

import com.example.hexagonal.app.demo.customer.application.command.UpdateCustomerNameCommand;
import com.example.hexagonal.app.demo.customer.domain.Customer;

public interface UpdateCustomerNameUseCase {
    Customer execute(UpdateCustomerNameCommand command);
}