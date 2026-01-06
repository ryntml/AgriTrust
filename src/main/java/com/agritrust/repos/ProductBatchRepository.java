package com.agritrust.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.enums.ProductStatus;

@Repository
public interface ProductBatchRepository extends JpaRepository<ProductBatchEntity, Long> {

	Optional<ProductBatchEntity> findById(Long productBatchId);
	
	List<ProductBatchEntity> findByName(String productName);
	
	List<ProductBatchEntity> findByProducerId(Integer producerId);

	List<ProductBatchEntity> findByStatus(ProductStatus status);	//active, sold or expired, may not use

	Optional<ProductBatchEntity> findByBatchCode(String batchCode);

}
