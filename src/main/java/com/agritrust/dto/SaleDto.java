package com.agritrust.dto;

import java.math.BigDecimal;

import com.agritrust.enums.EventType;

import jakarta.validation.constraints.NotNull;

public class SaleDto implements EventRequestDto {

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal price;

    private String description;

    @Override
    public EventType getEventType() {
        return EventType.SALE;
    }
}
