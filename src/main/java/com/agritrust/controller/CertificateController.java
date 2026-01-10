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

import com.agritrust.dto.CreateCertificateDto;
import com.agritrust.entity.CertificateEntity;
import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;
import com.agritrust.service.impl.CertificateService;
import com.agritrust.service.impl.ProductBatchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product/cert")
@RequiredArgsConstructor
public class CertificateController {

	private final CertificateService certService;
	private final ProductBatchService batchService;
	private final ModelMapper modelMapper;

	@PostMapping("/issue/{id}")
	// @PreAuthorize("hasRole('AUDITOR')")	//error döndürse de çalışıyor, galiba lazy-eager loadingle alakalı
	public ResponseEntity<CertificateEntity> issueCertificate(@PathVariable Long id, @Valid @RequestBody CreateCertificateDto dto,
			@AuthenticationPrincipal UserEntity auditor) {
		
		ProductBatchEntity batch = batchService.getById(id);
		CertificateEntity certificate = certService.addCertificate(dto,batch ,auditor);

		return ResponseEntity.status(HttpStatus.CREATED).body(certificate);
	}

	@GetMapping("/revoke/{certificateNumber}")
	// @PreAuthorize("hasAnyRole('AUDITOR')")
	public ResponseEntity revokeCertificate(@PathVariable String certificateNumber) {

		certService.revoke(certificateNumber);

		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@GetMapping("/{id}")//çalışmıyor
	// @PreAuthorize("hasAnyRole('ADMIN','AUDITOR','PRODUCER')")
	public ResponseEntity<List<CertificateEntity>> getCertificates(@PathVariable Long id) {

		List<CertificateEntity> certs = certService.getByProduct(id);
		return ResponseEntity.ok(certs);
	}
}
