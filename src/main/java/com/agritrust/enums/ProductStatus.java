package com.agritrust.enums;

public enum ProductStatus {
    /**
     * ProductBatchEntity is available and active in the supply chain
     */
    ACTIVE,

    /**
     * ProductBatchEntity has been sold to end consumer
     */
    SOLD,

    /**
     * ProductBatchEntity has expired or is no longer suitable for sale
     */
    EXPIRED
}
