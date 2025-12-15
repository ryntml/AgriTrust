package com.agritrust.entity;

import com.agritrust.entity.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Event entity for audit log and supply chain tracking
 * Maps to the 'events' table in PostgreSQL
 * Implements distributed event-log architecture
 */
@Entity
@Table(name = "events", indexes = {
        @Index(name = "idx_events_product", columnList = "product_id"),
        @Index(name = "idx_events_type", columnList = "event_type"),
        @Index(name = "idx_events_actor", columnList = "actor_id"),
        @Index(name = "idx_events_timestamp", columnList = "timestamp")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 50)
    private EventType eventType;

    @Column(length = 100)
    private String location; // Location where event occurred

    @Column(columnDefinition = "TEXT")
    private String description; // Event details

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Additional metadata stored as JSON
     * Can include temperature, humidity, quality score, etc.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    // Relationships

    /**
     * Product associated with this event
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * User who performed this action
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private User actor;
}
