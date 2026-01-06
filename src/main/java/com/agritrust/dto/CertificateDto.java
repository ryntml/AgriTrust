package com.agritrust.dto;

import com.agritrust.enums.EventType;

public class CertificateDto implements EventRequestDto {

    private String description;

    @Override
    public EventType getEventType() {
        return EventType.CERTIFICATE_ADD_OR_REVOKE;
    }
}
