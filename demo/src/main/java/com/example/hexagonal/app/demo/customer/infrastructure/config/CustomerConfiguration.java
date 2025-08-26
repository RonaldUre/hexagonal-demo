package com.example.hexagonal.app.demo.customer.infrastructure.config;

import com.example.hexagonal.app.demo.customer.application.ports.out.CustomerRepository;

import com.example.hexagonal.app.demo.customer.application.ports.in.RegisterCustomerUseCase;
import com.example.hexagonal.app.demo.customer.application.ports.in.UpdateCustomerUseCase;
import com.example.hexagonal.app.demo.customer.application.ports.in.GetCustomerByIdUseCase;
import com.example.hexagonal.app.demo.customer.application.ports.in.ListCustomersUseCase;
import com.example.hexagonal.app.demo.customer.application.ports.in.CheckEmailAvailabilityUseCase;

import com.example.hexagonal.app.demo.customer.application.service.RegisterCustomerService;
import com.example.hexagonal.app.demo.customer.application.service.UpdateCustomerService;
import com.example.hexagonal.app.demo.customer.application.query.GetCustomerByIdHandler;
import com.example.hexagonal.app.demo.customer.application.query.ListCustomersHandler;
import com.example.hexagonal.app.demo.customer.application.query.CheckEmailAvailabilityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfiguration {

    @Bean
    public RegisterCustomerUseCase registerCustomerService(CustomerRepository customerRepository) {
        return new RegisterCustomerService(customerRepository);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerService(CustomerRepository customerRepository) {
        return new UpdateCustomerService(customerRepository);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdHandler(CustomerRepository customerRepository) {
        return new GetCustomerByIdHandler(customerRepository);
    }

    @Bean
    public ListCustomersUseCase listCustomersHandler(CustomerRepository customerRepository) {
        return new ListCustomersHandler(customerRepository);
    }

    @Bean
    public CheckEmailAvailabilityUseCase checkEmailAvailabilityHandler(CustomerRepository customerRepository) {
        return new CheckEmailAvailabilityHandler(customerRepository);
    }
}