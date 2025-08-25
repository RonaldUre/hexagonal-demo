package com.example.hexagonal.app.demo.shared.outbox.outbound.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * JPA entity that maps directly to table 'outbox'.
 * Infraestructura (no dominio): mutable y con constructor por defecto para JPA.
 */
@Entity
@Table(name = "outbox")
public class OutboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aggregate_id", nullable = false)
    private Long aggregateId;

    @Column(name = "aggregate_type", length = 100, nullable = false)
    private String aggregateType;

    @Column(name = "type", length = 100, nullable = false)
    private String type;

    // MySQL 8 JSON column; almacenamos el JSON como String
    @Column(name = "payload", columnDefinition = "json", nullable = false)
    private String payload;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "processed", nullable = false)
    private boolean processed = false;

    // --- Constructors ---

    protected OutboxEntity() { /* JPA */ }

    public OutboxEntity(Long aggregateId, String aggregateType, String type, String payload) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.type = type;
        this.payload = payload;
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }

    public Long getAggregateId() { return aggregateId; }
    public void setAggregateId(Long aggregateId) { this.aggregateId = aggregateId; }

    public String getAggregateType() { return aggregateType; }
    public void setAggregateType(String aggregateType) { this.aggregateType = aggregateType; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public Instant getCreatedAt() { return createdAt; }

    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean processed) { this.processed = processed; }
}
