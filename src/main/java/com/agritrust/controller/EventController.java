package com.agritrust.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agritrust.dto.EventDto;
import com.agritrust.dto.ProcessDto;
import com.agritrust.dto.TransferDto;
import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;
import com.agritrust.service.impl.EventService;
import com.agritrust.service.impl.ProductBatchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class EventController {

	private final EventService eventService;
	private final ProductBatchService batchService;

	@PostMapping("/logistics/{batchId}")
	// @PreAuthorize("hasRole('DISTRIBUTOR') or hasRole('PRODUCER')")
	public ResponseEntity<Void> transferProduct(@PathVariable Long batchId, @Valid @RequestBody TransferDto dto,
			@AuthenticationPrincipal UserEntity transporter) {

		ProductBatchEntity batch = batchService.getById(batchId);
		eventService.recordEvent(batch, transporter, dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/logistics/{batchId}")
	public ResponseEntity<List<EventDto>> transferTrace(@PathVariable Long batchId) {
		return ResponseEntity.ok(eventService.getLogisticsTrace(batchId));
	}

	@PostMapping("/trans/{batchId}")
	// @PreAuthorize("hasRole('PRODUCER')")
	public ResponseEntity<Void> addProcessingEvent(@PathVariable Long batchId, @Valid @RequestBody ProcessDto dto,
			@AuthenticationPrincipal UserEntity processor) {
		
		ProductBatchEntity batch = batchService.getById(batchId);
		eventService.recordEvent(batch,processor, dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/trans/{batchId}")
	public ResponseEntity<List<EventDto>> processTrace(@PathVariable Long batchId) {
		return ResponseEntity.ok(eventService.getProcessingTrace(batchId));
	}
	
	@GetMapping("/trace/{batchId}")
	public ResponseEntity<List<EventDto>> getAllTrace(@PathVariable Long batchId) {
		return ResponseEntity.ok(eventService.getFullTrace(batchId));
	}
}
