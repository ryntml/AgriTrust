package com.agritrust.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agritrust.dto.CreateProductBatchDto;
import com.agritrust.dto.HarvestDto;
import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;
import com.agritrust.enums.ProductStatus;
import com.agritrust.repos.ProductBatchRepository;
import com.agritrust.service.ProductBatchReadable;
import com.agritrust.service.ProductBatchWritable;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductBatchService implements ProductBatchReadable, ProductBatchWritable{

	private final ProductBatchRepository batchRepo;
	private final EventService eventService;
	private final ModelMapper modelMapper;
	
	private String generateBatchCode() {
	    return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	}
	
	@Override
	public List<ProductBatchEntity> getList() {
		return batchRepo.findAll();
	}

	@Override
	public ProductBatchEntity getById(Long id) {
		return batchRepo.findById(id).orElseThrow(()-> new IllegalArgumentException());
	}

	@Override
	public ProductBatchEntity createBatch(CreateProductBatchDto dto, UserEntity producer) {
	    ProductBatchEntity batch = modelMapper.map(dto, ProductBatchEntity.class);

	    batch.setBatchCode(generateBatchCode());
	    batch.setProducer(producer);
	    batch.setStatus(ProductStatus.ACTIVE);
	    
	    try {
			batchRepo.save(batch);	//also returns entity, no problem for now.
		} 
	    catch (Exception e) {
			System.out.println("Problem saving batch: " + e.getMessage());	//better error handling later
		}
	    
	    eventService.recordEvent(		//start event chain
	            batch,
	            producer,
	            new HarvestDto(dto.getInitialPrice(), dto.getQuantity(), "Initial harvest")
	    );
	    return batch;
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
