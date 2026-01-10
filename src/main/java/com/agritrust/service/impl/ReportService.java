package com.agritrust.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agritrust.entity.ReportEntity;
import com.agritrust.repos.ReportRepository;
import com.agritrust.service.ReportReadable;
import com.agritrust.service.ReportWritable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService implements ReportReadable,ReportWritable{

	private final ReportRepository reportRepo;
	
	@Override
	public List<ReportEntity> getList() {
		return reportRepo.findAll();
	}

	public List<ReportEntity> getNotReviewedList() {
		return reportRepo.findByReviewedFalse();
	}
	
	@Override
	public ReportEntity getById(Long id) {
		return reportRepo.findById(id).orElseThrow(()-> new IllegalArgumentException());
	}

	@Override
	public void add(ReportEntity entity) {
		reportRepo.save(entity);
		
	}

}
