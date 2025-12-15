package com.agritrust.entity.enums;

/**
 * Event type enumeration for supply chain tracking
 * Used in the distributed event-log architecture to track product movements
 */
public enum EventType {
    /**
     * Product was harvested from the farm
     */
    HARVESTED,

    /**
     * Product is being transported between locations
     */
    TRANSPORTED,

    /**
     * Product was stored in a warehouse or facility
     */
    STORED,

    /**
     * Product was sold to another party
     */
    SOLD,

    /**
     * Product underwent quality inspection
     */
    INSPECTED
}
