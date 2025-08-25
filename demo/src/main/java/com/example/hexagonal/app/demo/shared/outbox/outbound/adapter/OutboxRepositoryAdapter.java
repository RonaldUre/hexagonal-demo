package com.example.hexagonal.app.demo.shared.outbox.outbound.adapter;

import com.example.hexagonal.app.demo.shared.outbox.application.ports.OutboxRepository;
import com.example.hexagonal.app.demo.shared.outbox.domain.*;
import com.example.hexagonal.app.demo.shared.outbox.outbound.entity.OutboxEntity;
import com.example.hexagonal.app.demo.shared.outbox.outbound.repository.OutboxJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OutboxRepositoryAdapter implements OutboxRepository {

    private final OutboxJpaRepository jpa;

    public OutboxRepositoryAdapter(OutboxJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public OutboxMessage save(OutboxMessage message) {
        OutboxEntity entity = toEntity(message);
        OutboxEntity saved = jpa.save(entity);

        // asignar id generado y rehidratar
        if (message.id() == null && saved.getId() != null) {
            message.assignId(OutboxId.of(saved.getId()));
        }
        return OutboxMessage.rehydrate(
                OutboxId.of(saved.getId()),
                AggregateRef.of(saved.getAggregateType(), saved.getAggregateId()),
                EventType.of(saved.getType()),
                SerializedPayload.of(saved.getPayload()),
                saved.getCreatedAt(),
                saved.isProcessed()
        );
    }

    @Override
    public Optional<OutboxMessage> findById(OutboxId id) {
        return jpa.findById(id.asLong())
                .map(this::toDomain);
    }

    @Override
    public List<OutboxMessage> findUnprocessed(int limit) {
        var pageable = PageRequest.of(0, Math.max(1, limit), Sort.by(Sort.Direction.ASC, "createdAt"));
        return jpa.findByProcessedFalse(pageable)
                 .stream()
                 .map(this::toDomain)
                 .toList();
    }

    @Override
    public void markProcessed(OutboxId id) {
        jpa.findById(id.asLong()).ifPresent(entity -> {
            entity.setProcessed(true);
            jpa.save(entity);
        });
    }

    // -------- mapping helpers --------

    private OutboxEntity toEntity(OutboxMessage m) {
        OutboxEntity e = new OutboxEntity(
                m.aggregateRef().aggregateId(),
                m.aggregateRef().aggregateType(),
                m.eventType().asString(),
                m.payload().asString()
        );
        // Si ya tiene id (rehidratado), lo dejamos para que JPA haga merge
        // createdAt lo maneja @CreationTimestamp
        e.setProcessed(m.processed());
        return e;
    }

    private OutboxMessage toDomain(OutboxEntity e) {
        return OutboxMessage.rehydrate(
                OutboxId.of(e.getId()),
                AggregateRef.of(e.getAggregateType(), e.getAggregateId()),
                EventType.of(e.getType()),
                SerializedPayload.of(e.getPayload()),
                e.getCreatedAt(),
                e.isProcessed()
        );
    }
}
