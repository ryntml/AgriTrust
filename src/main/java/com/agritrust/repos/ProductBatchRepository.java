package com.agritrust.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.enums.ProductStatus;

public interface ProductBatchRepository extends JpaRepository<ProductBatchEntity, Long> {

	List<ProductBatchEntity> findByProducerId(Integer producerId);

	List<ProductBatchEntity> findByStatus(ProductStatus status);	//Admin / auditor filtering might not use

	Optional<ProductBatchEntity> findByBatchCode(String batchCode);
}
