package com.example.hexagonal.app.demo.customer.application.ports;

import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Email;

import java.util.List;
import java.util.Optional;

/**
 * Outbound Port for Customer persistence.
 * Application layer depends on this interface; infrastructure provides an adapter (e.g., JPA).
 *
 * No framework types here (no Spring Data, no Pageable).
 */
public interface CustomerRepository {

    /**
     * Persists a Customer aggregate.
     * - If it's new, assigns an id (the adapter will call customer.assignId(...)).
     * - Returns the up-to-date aggregate (with id, timestamps if any).
     */
    Customer save(Customer customer);

    /**
     * Loads a Customer by id.
     */
    Optional<Customer> findById(CustomerId id);

    /**
     * Fast uniqueness check for Email.
     * Useful before creating a new Customer to enforce unique email.
     */
    boolean existsByEmail(Email email);

    /**
     * Simple pagination without framework types.
     * page >= 0, size > 0.
     * (Si luego prefieres otro contrato —p.ej. offset/limit o un Page<T> propio— lo ajustamos.)
     */
    List<Customer> findAll(int page, int size);
}
