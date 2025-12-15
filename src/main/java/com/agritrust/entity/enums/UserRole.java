package com.agritrust.entity.enums;

/**
 * User role enumeration for AgriTrust system
 * Defines different types of users in the agricultural supply chain
 */
public enum UserRole {
    /**
     * System administrator with full access
     */
    ADMIN,

    /**
     * Agricultural producer/farmer
     */
    PRODUCER,

    /**
     * End consumer
     */
    CONSUMER,

    /**
     * Distributor/middleman in the supply chain
     */
    DISTRIBUTOR
}
