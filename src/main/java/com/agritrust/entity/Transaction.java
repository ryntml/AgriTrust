package com.agritrust.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction entity for tracking transfers in the supply chain
 * Maps to the 'transactions' table in PostgreSQL
 * Used by Compliance Engine for markup calculation and monitoring
 */
@Entity
@Table(name = "transactions", indexes = {
        @Index(name = "idx_transactions_product", columnList = "product_id"),
        @Index(name = "idx_transactions_from", columnList = "from_node_id"),
        @Index(name = "idx_transactions_to", columnList = "to_node_id"),
        @Index(name = "idx_transactions_date", columnList = "transaction_date"),
        @Index(name = "idx_transactions_flagged", columnList = "is_flagged")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity; // Amount transferred

    @Column(name = "price_per_unit", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerUnit; // Unit price

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount; // Total transaction amount

    /**
     * Markup percentage for compliance monitoring
     * Used to detect unethical price increases
     */
    @Column(name = "markup_percentage", precision = 5, scale = 2)
    private BigDecimal markupPercentage;

    @Column(name = "transaction_date")
    @Builder.Default
    private LocalDateTime transactionDate = LocalDateTime.now();

    /**
     * Flag for unethical markup detection
     * Set to true by Compliance Engine if markup exceeds threshold
     */
    @Column(name = "is_flagged")
    @Builder.Default
    private Boolean isFlagged = false;

    @Column(columnDefinition = "TEXT")
    private String notes; // Additional transaction notes

    // Relationships

    /**
     * Product being transferred
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Sender (PRODUCER, DISTRIBUTOR, etc.)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_node_id")
    private User fromNode;

    /**
     * Receiver (DISTRIBUTOR, CONSUMER, etc.)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_node_id", nullable = false)
    private User toNode;
}
