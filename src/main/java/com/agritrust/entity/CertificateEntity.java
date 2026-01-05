package com.agritrust.entity;

import java.time.LocalDate;
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

@Entity(name = "certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * External certificate identifier
     * (issued by third-party authority)
     */
    @Column(nullable = false, unique = true, length = 100)
    private String certificateNumber;

    @Column(nullable = false, length = 100)
    private String certificateType;
    // e.g. ORGANIC, FAIR_TRADE, ISO_22000

    @Column(nullable = false, length = 150)
    private String issuingAuthority;
    
    @Column(nullable = false, length = 150)
    @JoinColumn(name = "auditor_id")
    private UserEntity auditor;

    @Column
    private LocalDate issuedDate;

    @Column
    private LocalDate validUntil;

    /**
     * Product batch this certificate applies to
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_batch_id")
    private ProductBatchEntity productBatch;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column
    private boolean revoked;
}

