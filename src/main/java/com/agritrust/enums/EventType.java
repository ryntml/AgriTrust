package com.agritrust.enums;

public enum EventType {
    /**
     * Product was harvested from the farm
     */
    HARVESTED,

    /**
     * Product is being transported between locations
     */
    TRANSPORT,

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
    INSPECT,
    /**
     * Product was used in another's production
     */
    PROCESSED
}
