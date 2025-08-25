package com.example.hexagonal.app.demo.shared.outbox.outbound.repository;

import com.example.hexagonal.app.demo.shared.outbox.outbound.entity.OutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Outbox repository at infrastructure layer.
 * This is NOT the hexagonal port, it's the raw Spring Data adapter.
 */
public interface OutboxJpaRepository extends JpaRepository<OutboxEntity, Long> {

    // Retrieve unprocessed messages to be dispatched
    List<OutboxEntity> findByProcessedFalse();

    List<OutboxEntity> findByProcessedFalse(org.springframework.data.domain.Pageable pageable);
}