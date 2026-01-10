package com.agritrust.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agritrust.entity.ReportEntity;
import com.agritrust.entity.UserEntity;

public interface ReportRepository extends JpaRepository<ReportEntity,Long>{
	List<ReportEntity> findByReviewedFalse();
}
