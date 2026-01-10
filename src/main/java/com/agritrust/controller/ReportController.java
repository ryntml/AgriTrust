package com.agritrust.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.agritrust.dto.ReportDto;
import com.agritrust.entity.ReportEntity;
import com.agritrust.service.impl.ReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;
	private final ModelMapper modelMapper;
	
	@PostMapping("/report")
	public ResponseEntity<Void> reportAbuse(@Valid @RequestBody ReportDto dto) {
		
		ReportEntity report = modelMapper.map(dto, ReportEntity.class);
		reportService.add(report);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/report")
	public ResponseEntity<List<ReportEntity>> seeReports() {
		
		List<ReportEntity> reports = reportService.getList();
		return ResponseEntity.ok(reports);
	}
	
	@GetMapping("admin/report")
	public ResponseEntity<List<ReportEntity>> viewReports() {
		
		List<ReportEntity> reportsToView = reportService.getNotReviewedList();
		return ResponseEntity.ok(reportsToView);
	}
	
	@GetMapping("admin/report/{id}")
	public ResponseEntity<ReportEntity> viewById(@PathVariable Long id) {
		
		return ResponseEntity.ok(reportService.getById(id));
	}
}
