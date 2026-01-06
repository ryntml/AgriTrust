package com.agritrust.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agritrust.entity.CertificateEntity;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity,Long>{
	Optional<CertificateEntity> findByCertificateNumber(String certificateNumber);
	Optional<CertificateEntity> findByCertificateNumberAndRevokedFalse(String certificateNumber);
	List<CertificateEntity> findByRevokedFalse();
	List<CertificateEntity> findAllByAuditor(Long auditorCitizenId);
	List<CertificateEntity> findByProductBatch(Long id);
	
}
