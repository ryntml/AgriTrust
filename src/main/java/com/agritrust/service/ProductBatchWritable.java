package com.agritrust.service;

import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.enums.ProductStatus;

public interface ProductBatchWritable extends Writable<ProductBatchEntity,Long>{
	void changeBatchStatus(ProductStatus status, Long id);
}
