package com.agritrust.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.agritrust.entity.UserEntity;
import com.agritrust.enums.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBatchResponseDto {

    private String batchCode;

    private String name;

    private String category; // Meyve, Sebze, Tahıl...

    private String origin; // Manisa, Ordu, etc.

    private BigDecimal initialPrice; // Harvest price

    private BigDecimal quantity;

    private String unit; // kg, ton, piece

    private UserEntity producer;		//return producer dto here

    private ProductStatus status;

    private LocalDateTime createdAt;
}
