package com.agritrust.service;

import com.agritrust.dto.CreateProductBatchDto;
import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;
import com.agritrust.enums.ProductStatus;

public interface ProductBatchWritable{
	void changeBatchStatus(ProductStatus status, Long id);
	ProductBatchEntity createBatch(CreateProductBatchDto dto, UserEntity producer);
}
