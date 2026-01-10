package com.agritrust.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agritrust.dto.CertificateEventDto;
import com.agritrust.dto.CreateCertificateDto;
import com.agritrust.entity.CertificateEntity;
import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;
import com.agritrust.repos.CertificateRepository;
import com.agritrust.service.CertificateReadable;
import com.agritrust.service.CertificateWritable;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class CertificateService implements CertificateReadable,CertificateWritable{

	private CertificateRepository certRepo;
	private final ModelMapper modelMapper;
	private final EventService eventService;
	
	@Override
	public List<CertificateEntity> getList() {
		return certRepo.findAll();
	}

	@Override
	public CertificateEntity getById(Long id) {
		return certRepo.findById(id).orElseThrow(()-> new IllegalArgumentException());
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
		return certRepo.findByCertificateNumber(certificateNumber).orElseThrow(()-> new IllegalArgumentException());
	}

	@Override
	public List<CertificateEntity> getAllValid() {
		return certRepo.findByRevokedFalse();
	}

	@Override
	public List<CertificateEntity> getByAuditor(Integer auditorId) {//şüpheli
		return certRepo.findAllByAuditorId(auditorId);
	}

	@Override
	public CertificateEntity addCertificate(CreateCertificateDto dto, ProductBatchEntity product, UserEntity auditor) {
		CertificateEntity certificate = modelMapper.map(dto, CertificateEntity.class);

	    certificate.setProductBatch(product);
		certRepo.save(certificate);	
	    
	    eventService.recordEvent(		//start event chain
	            product,
	            auditor,
	            new CertificateEventDto("New certificate added for batch " + product.getBatchCode())
	    );
	    return certificate;
	}

	@Override
	public List<CertificateEntity> getByProduct(Long id) {
		return certRepo.findByProductBatchId(id);
	}

}
