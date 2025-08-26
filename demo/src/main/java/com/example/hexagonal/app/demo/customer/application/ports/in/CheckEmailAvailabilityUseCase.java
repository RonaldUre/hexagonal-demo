package com.example.hexagonal.app.demo.customer.application.ports.in;

import com.example.hexagonal.app.demo.customer.application.query.CheckEmailAvailabilityQuery;

public interface CheckEmailAvailabilityUseCase {
    boolean handle(CheckEmailAvailabilityQuery query);
}