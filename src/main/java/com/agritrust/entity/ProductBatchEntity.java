package com.agritrust.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "product_batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Public identifier for QR / citizen lookup
     */
    @Column(nullable = false, unique = true, updatable = false)
    private String batchCode;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String category; // Meyve, Sebze, Tahıl...

    @Column(nullable = false, length = 100)
    private String origin; // Manisa, Ordu, etc.

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal initialPrice; // Harvest price

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(nullable = false, length = 20)
    private String unit; // kg, ton, piece

    /**
     * Who registered the product (must be PRODUCER)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id", nullable = false)
    private UserEntity producer;

    /**
     * Soft delete / lifecycle marker
     */
    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
