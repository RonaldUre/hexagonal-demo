package com.example.hexagonal.app.demo.customer.infrastructure.config;

import com.example.hexagonal.app.demo.customer.application.ports.CustomerRepository;
import com.example.hexagonal.app.demo.customer.application.service.RegisterCustomerService;
import com.example.hexagonal.app.demo.customer.application.service.UpdateCustomerService;
import com.example.hexagonal.app.demo.customer.application.query.CheckEmailAvailabilityHandler;
import com.example.hexagonal.app.demo.customer.application.query.GetCustomerByIdHandler;
import com.example.hexagonal.app.demo.customer.application.query.ListCustomersHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfiguration {

    @Bean
    public RegisterCustomerService registerCustomerService(CustomerRepository customerRepository) {
        return new RegisterCustomerService(customerRepository);
    }

    @Bean
    public UpdateCustomerService updateCustomerService(CustomerRepository customerRepository) {
        return new UpdateCustomerService(customerRepository);
    }

    @Bean
    public GetCustomerByIdHandler getCustomerByIdHandler(CustomerRepository customerRepository) {
        return new GetCustomerByIdHandler(customerRepository);
    }

    @Bean
    public ListCustomersHandler listCustomersHandler(CustomerRepository customerRepository) {
        return new ListCustomersHandler(customerRepository);
    }

    @Bean
    public CheckEmailAvailabilityHandler checkEmailAvailabilityHandler(CustomerRepository customerRepository) {
        return new CheckEmailAvailabilityHandler(customerRepository);
    }
}