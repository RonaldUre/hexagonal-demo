package com.example.hexagonal.app.demo.customer.infrastructure.persistence.repository;

import com.example.hexagonal.app.demo.customer.infrastructure.persistence.entity.JpaCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<JpaCustomerEntity, Long> {
    boolean existsByEmail(String email);
}
