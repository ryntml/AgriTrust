package com.agritrust.dto;

import com.agritrust.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDto implements EventRequestDto {

    private String description;

    @Override
    public EventType getEventType() {
        return EventType.CERTIFICATE_ADD_OR_REVOKE;
    }
}
