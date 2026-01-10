package com.agritrust.messaging;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.agritrust.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RabbitMQ üzerinden gönderilen Event mesajı
 * Async event logging için kullanılır
 * 
 * @author Reyyan (Infrastructure)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productBatchId;
    private String batchCode;
    private Integer actorId;
    private String actorUsername;
    private EventType eventType;
    private BigDecimal price;
    private String fromLocation;
    private String toLocation;
    private BigDecimal quantity;
    private String description;
    private LocalDateTime timestamp;
}
