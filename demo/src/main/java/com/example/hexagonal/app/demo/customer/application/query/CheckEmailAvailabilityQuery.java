package com.example.hexagonal.app.demo.customer.application.query;

import com.example.hexagonal.app.demo.customer.domain.model.vo.Email;

public record CheckEmailAvailabilityQuery(String email) {
    public CheckEmailAvailabilityQuery {
        // Validación mínima aquí; el VO hará la validación real
        if (email == null) throw new IllegalArgumentException("email must not be null");
    }

    public Email toEmail() {
        return Email.of(email);
    }
}
