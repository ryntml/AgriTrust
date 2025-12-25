package com.agritrust.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.agritrust.enums.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------------------------
       EVENT TYPE
    ----------------------------*/
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EventType eventType;

    /* ---------------------------
       PRODUCT BATCH
    ----------------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_batch_id", nullable = false)
    private ProductBatchEntity productBatch;

    /* ---------------------------
       ACTOR (WHO DID THIS)
    ----------------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", nullable = false)
    private UserEntity actor;

    /* ---------------------------
       EVENT DATA (GENERIC FIELDS)
    ----------------------------*/

    @Column(precision = 10, scale = 2)
    private BigDecimal price;  
    // Used for SALE, PROCESSING cost, etc.

    @Column(length = 100)
    private String fromLocation; // TRANSFER

    @Column(length = 100)
    private String toLocation;   // TRANSFER

    @Column(precision = 10, scale = 2)
    private BigDecimal quantity; // partial processing / loss

    @Column(length = 255)
    private String description;

    /* ---------------------------
       TIMESTAMP
    ----------------------------*/
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
