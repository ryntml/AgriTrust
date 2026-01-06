package com.agritrust.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;
import com.agritrust.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {		//kayıtları kullanıcıya göstermek için
	private EventType eventType;

    private ProductBatchEntity productBatch;

    private UserEntity actor;

    /* ---------------------------
       EVENT DATA (GENERIC FIELDS)
    ----------------------------*/

    private BigDecimal price;  // Used for SALE, PROCESSING cost, etc.

    private String fromLocation; // TRANSFER

    private String toLocation;   // TRANSFER

    private BigDecimal quantity; // partial processing / loss

    private String description;

    private LocalDateTime createdAt;
}
