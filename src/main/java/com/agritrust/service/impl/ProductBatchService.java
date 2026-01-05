package com.agritrust.service.impl;

import java.util.List;

import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.enums.ProductStatus;
import com.agritrust.repos.ProductBatchRepository;
import com.agritrust.service.ProductBatchReadable;
import com.agritrust.service.ProductBatchWritable;

public class ProductBatchService implements ProductBatchReadable, ProductBatchWritable{

	private ProductBatchRepository batchRepo;
	@Override
	public List<ProductBatchEntity> getList() {
		return batchRepo.findAll();
	}

	@Override
	public ProductBatchEntity getById(Long id) {
		return batchRepo.findById(id).orElseThrow(()-> new IllegalArgumentException());
	}

	@Override
	public void add(ProductBatchEntity entity) {
		batchRepo.save(entity);	//entity must not be null, handle
		
	}

	@Override
	public void changeBatchStatus(ProductStatus status, Long id) {
		ProductBatchEntity batchToChange = batchRepo.findById(id).orElseThrow(()-> new IllegalArgumentException());
		batchToChange.setStatus(status);
		batchRepo.save(batchToChange);
	}

	@Override
	public ProductBatchEntity getBatchByCode(String batchCode) {
		return batchRepo.findByBatchCode(batchCode).orElseThrow(()-> new IllegalArgumentException());
	}

	@Override
	public List<ProductBatchEntity> getBatchesByProducer(Integer producerId) {
		return batchRepo.findByProducerId(producerId);
	}

}
