package com.agritrust.dto;

import java.math.BigDecimal;

import com.agritrust.enums.EventType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HarvestDto implements EventRequestDto {

    @NotNull
    private BigDecimal price;	

    @NotNull
    private BigDecimal quantity;

    @NotBlank
    private String description;

    @Override
    public EventType getEventType() {
        return EventType.HARVEST;
    }
}
