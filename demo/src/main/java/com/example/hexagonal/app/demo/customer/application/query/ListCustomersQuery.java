package com.example.hexagonal.app.demo.customer.application.query;


public record ListCustomersQuery(int page, int size) {
    public ListCustomersQuery {
        // reglas básicas (ajusta límites si quieres)
        if (page < 0) throw new IllegalArgumentException("page must be >= 0");
        if (size <= 0) throw new IllegalArgumentException("size must be > 0");
        if (size > 100) throw new IllegalArgumentException("size must be <= 100");
    }
}
