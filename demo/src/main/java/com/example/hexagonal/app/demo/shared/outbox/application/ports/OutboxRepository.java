package com.example.hexagonal.app.demo.shared.outbox.application.ports;

import com.example.hexagonal.app.demo.shared.outbox.domain.OutboxId;
import com.example.hexagonal.app.demo.shared.outbox.domain.OutboxMessage;

import java.util.List;
import java.util.Optional;

public interface OutboxRepository {

    /** Persiste un nuevo OutboxMessage o devuelve el mismo con id asignado. */
    OutboxMessage save(OutboxMessage message);

    /** Obtiene por id. Útil para inspección o depuración. */
    Optional<OutboxMessage> findById(OutboxId id);

    /**
     * Lote de mensajes no procesados (processed = false),
     * ordenados por created_at asc para mantener orden lógico.
     */
    List<OutboxMessage> findUnprocessed(int limit);

    /** Marca un mensaje como procesado (idempotente). */
    void markProcessed(OutboxId id);
}