package com.agritrust.service;

import java.util.List;

import com.agritrust.entity.CertificateEntity;

public interface CertificateReadable extends Readable<CertificateEntity, Long>{
	CertificateEntity getByCertificateNumber(String certificateNumber);
	List<CertificateEntity> getAllValid();
	List<CertificateEntity> getByAuditor(Integer auditorId);
}
