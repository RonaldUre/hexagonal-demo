package com.example.hexagonal.app.demo.customer.infrastructure.mapper;

import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Email;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Name;
import com.example.hexagonal.app.demo.customer.infrastructure.persistence.entity.JpaCustomerEntity;

import java.time.Instant;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static JpaCustomerEntity toEntity(Customer agg) {
        Long id = agg.id() != null ? agg.id().asLong() : null;
        // created_at lo completa la DB (DEFAULT CURRENT_TIMESTAMP)
        return new JpaCustomerEntity(id, agg.name().asString(), agg.email().asString(), null);
    }

    public static Customer toAggregate(JpaCustomerEntity e) {
        Instant created = e.getCreatedAt() != null ? e.getCreatedAt().toInstant() : Instant.now();
        return Customer.rehydrate(
                CustomerId.of(e.getId()),
                Email.of(e.getEmail()),
                Name.of(e.getName()),
                created
        );
    }
}
