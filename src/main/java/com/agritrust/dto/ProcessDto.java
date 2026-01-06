package com.agritrust.dto;

import java.math.BigDecimal;

import com.agritrust.enums.EventType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDto implements EventRequestDto {

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal price;

    private String description;

    @Override
    public EventType getEventType() {
        return EventType.PROCESSING;
    }
}