package com.agritrust.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agritrust.entity.CertificateEntity;
import com.agritrust.repos.CertificateRepository;
import com.agritrust.service.CertificateReadable;
import com.agritrust.service.CertificateWritable;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class CertificateService implements CertificateReadable,CertificateWritable{

	private CertificateRepository certRepo;
	
	@Override
	public List<CertificateEntity> getList() {
		return certRepo.findAll();
	}

	@Override
	public CertificateEntity getById(Long id) {
		return certRepo.findById(id).orElseThrow(()-> new IllegalArgumentException());
	}

	@Override
	public void add(CertificateEntity entity) {
		certRepo.save(entity);
		
	}

	@Override
	public void revoke(String certificateNumber) {
		CertificateEntity certToRevoke = certRepo.findByCertificateNumberAndRevokedFalse(certificateNumber)
				.orElseThrow(()-> new IllegalArgumentException());;
		certToRevoke.setRevoked(true);
		certRepo.save(certToRevoke);
	}

	@Override
	public CertificateEntity getByCertificateNumber(String certificateNumber) {
		return certRepo.findByCertificateNumberAndRevokedFalse(certificateNumber).orElseThrow(()-> new IllegalArgumentException());
	}

	@Override
	public List<CertificateEntity> getAllValid() {
		return certRepo.findByRevokedFalse();
	}

	@Override
	public List<CertificateEntity> getByAuditor(Long auditorId) {//şüpheli
		return certRepo.findAllByAuditor(auditorId);
	}

}
