package com.agritrust.service;

import java.util.List;

import com.agritrust.entity.ProductBatchEntity;

public interface ProductBatchReadable extends Readable<ProductBatchEntity,Long>{
    ProductBatchEntity getBatchByCode(String batchCode);

    List<ProductBatchEntity> getBatchesByProducer(Integer producerId);
}
