package com.agritrust.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agritrust.dto.CreateProductBatchDto;
import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;
import com.agritrust.service.impl.ProductBatchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductBatchController {

	private final ProductBatchService productBatchService;
	private final ModelMapper modelMapper;

	@PostMapping
	// @PreAuthorize("hasRole('PRODUCER')")
	public ResponseEntity<ProductBatchEntity> createBatch(@Valid @RequestBody CreateProductBatchDto dto,
			@AuthenticationPrincipal UserEntity producer) {
		ProductBatchEntity batch = productBatchService.createBatch(dto, producer);

		//ProductBatchResponseDTO response = modelMapper.map(batch, ProductBatchResponseDTO.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(batch);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAnyRole('ADMIN','AUDITOR','PRODUCER','DISTRIBUTOR')")
	public ResponseEntity<ProductBatchEntity> getBatch(@PathVariable Long id) {

		ProductBatchEntity batch = productBatchService.getById(id);

		return ResponseEntity.ok(batch
		// modelMapper.map(batch, ProductBatchResponseDTO.class)
		);
	}

	@GetMapping
	// @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
	public ResponseEntity<List<ProductBatchEntity>> getAllBatches() {

		return ResponseEntity.ok(productBatchService.getList()
//                        .stream()
//                        .map(b -> modelMapper.map(b, ProductBatchResponseDTO.class))
//                        .toList()
		);
	}
}
