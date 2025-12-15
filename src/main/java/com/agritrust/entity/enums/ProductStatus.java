package com.agritrust.entity.enums;

/**
 * Product status enumeration for AgriTrust supply chain
 * Tracks the lifecycle state of agricultural products
 */
public enum ProductStatus {
    /**
     * Product is available and active in the supply chain
     */
    ACTIVE,

    /**
     * Product has been sold to end consumer
     */
    SOLD,

    /**
     * Product has expired or is no longer suitable for sale
     */
    EXPIRED
}
