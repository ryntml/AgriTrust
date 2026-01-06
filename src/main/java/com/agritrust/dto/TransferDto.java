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
public class TransferDto implements EventRequestDto {

    @NotBlank
    private String fromLocation;

    @NotBlank
    private String toLocation;

    @NotNull
    private BigDecimal price;

    @Override
    public EventType getEventType() {
        return EventType.TRANSFER;
    }
}
