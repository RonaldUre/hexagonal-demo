package com.example.hexagonal.app.demo.customer.infrastructure.web;

import com.example.hexagonal.app.demo.customer.application.command.RegisterCustomerCommand;
import com.example.hexagonal.app.demo.customer.application.command.UpdateCustomerCommand;
import com.example.hexagonal.app.demo.customer.application.query.CheckEmailAvailabilityQuery;
import com.example.hexagonal.app.demo.customer.application.query.GetCustomerByIdQuery;
import com.example.hexagonal.app.demo.customer.application.query.ListCustomersQuery;
import com.example.hexagonal.app.demo.customer.application.service.RegisterCustomerService;
import com.example.hexagonal.app.demo.customer.application.service.UpdateCustomerService;
import com.example.hexagonal.app.demo.customer.application.query.GetCustomerByIdHandler;
import com.example.hexagonal.app.demo.customer.application.query.ListCustomersHandler;
import com.example.hexagonal.app.demo.customer.application.query.CheckEmailAvailabilityHandler;
import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.infrastructure.web.dto.CustomerResponse;
import com.example.hexagonal.app.demo.customer.infrastructure.web.dto.RegisterCustomerRequest;
import com.example.hexagonal.app.demo.customer.infrastructure.web.dto.UpdateCustomerRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final RegisterCustomerService registerCustomerService;
    private final UpdateCustomerService updateCustomerService;
    private final GetCustomerByIdHandler getCustomerByIdHandler;
    private final ListCustomersHandler listCustomersHandler;
    private final CheckEmailAvailabilityHandler checkEmailAvailabilityHandler;

    public CustomerController(
            RegisterCustomerService registerCustomerService,
            UpdateCustomerService updateCustomerService,
            GetCustomerByIdHandler getCustomerByIdHandler,
            ListCustomersHandler listCustomersHandler,
            CheckEmailAvailabilityHandler checkEmailAvailabilityHandler) {
        this.registerCustomerService = registerCustomerService;
        this.updateCustomerService = updateCustomerService;
        this.getCustomerByIdHandler = getCustomerByIdHandler;
        this.listCustomersHandler = listCustomersHandler;
        this.checkEmailAvailabilityHandler = checkEmailAvailabilityHandler;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(@Valid @RequestBody RegisterCustomerRequest request) {
        RegisterCustomerCommand command = new RegisterCustomerCommand(request.name(), request.email());
        Customer customer = registerCustomerService.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerResponse.fromDomain(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        GetCustomerByIdQuery query = new GetCustomerByIdQuery(id);
        Customer customer = getCustomerByIdHandler.handle(query);
        return ResponseEntity.ok(CustomerResponse.fromDomain(customer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> listCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        ListCustomersQuery query = new ListCustomersQuery(page, size);
        List<Customer> customers = listCustomersHandler.handle(query);
        List<CustomerResponse> response = customers.stream()
                .map(CustomerResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request) {
        UpdateCustomerCommand command = new UpdateCustomerCommand(id, request.name(), request.email());
        Customer customer = updateCustomerService.execute(command);
        return ResponseEntity.ok(CustomerResponse.fromDomain(customer));
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailAvailability(@RequestParam String email) {
        CheckEmailAvailabilityQuery query = new CheckEmailAvailabilityQuery(email);
        boolean available = checkEmailAvailabilityHandler.handle(query);
        return ResponseEntity.ok(available);
    }
}