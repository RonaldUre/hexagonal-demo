package com.example.hexagonal.app.demo.customer.infrastructure.persistence.adapter;

import com.example.hexagonal.app.demo.customer.application.ports.CustomerRepository;
import com.example.hexagonal.app.demo.customer.domain.Customer;
import com.example.hexagonal.app.demo.customer.domain.model.vo.CustomerId;
import com.example.hexagonal.app.demo.customer.domain.model.vo.Email;
import com.example.hexagonal.app.demo.customer.infrastructure.mapper.CustomerMapper;
import com.example.hexagonal.app.demo.customer.infrastructure.persistence.entity.JpaCustomerEntity;
import com.example.hexagonal.app.demo.customer.infrastructure.persistence.repository.CustomerJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class CustomerRepositoryJpaAdapter implements CustomerRepository {

    private final CustomerJpaRepository jpa;

    public CustomerRepositoryJpaAdapter(CustomerJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        JpaCustomerEntity entity = CustomerMapper.toEntity(customer);
        JpaCustomerEntity saved = jpa.save(entity);

        // Si era nuevo, asigna el id al aggregate existente
        if (customer.id() == null) {
            customer.assignId(CustomerId.of(saved.getId()));
        }

        // Devolvemos el aggregate rehidratado para incluir created_at de DB
        return CustomerMapper.toAggregate(saved);
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        return jpa.findById(id.asLong()).map(CustomerMapper::toAggregate);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpa.existsByEmail(email.asString());
    }

    @Override
    public List<Customer> findAll(int page, int size) {
        var sort = Sort.by(Sort.Direction.DESC, "id");
        return jpa.findAll(PageRequest.of(page, size, sort))
                  .map(CustomerMapper::toAggregate)
                  .getContent();
    }
}
