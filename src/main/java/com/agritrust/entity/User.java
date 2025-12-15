package com.agritrust.entity;

import com.agritrust.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity for authentication and authorization
 * Maps to the 'users' table in PostgreSQL
 * Used by JWT authentication system
 */
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_username", columnList = "username"),
        @Index(name = "idx_users_email", columnList = "email"),
        @Index(name = "idx_users_role", columnList = "role")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password; // BCrypt hashed password

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private UserRole role = UserRole.CONSUMER;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    // Relationships

    /**
     * Products produced by this user (if role is PRODUCER)
     */
    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> producedProducts = new ArrayList<>();

    /**
     * Events where this user was the actor
     */
    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Event> events = new ArrayList<>();

    /**
     * Transactions where this user sent products
     */
    @OneToMany(mappedBy = "fromNode", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Transaction> sentTransactions = new ArrayList<>();

    /**
     * Transactions where this user received products
     */
    @OneToMany(mappedBy = "toNode", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Transaction> receivedTransactions = new ArrayList<>();
}
