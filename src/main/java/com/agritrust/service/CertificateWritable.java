package com.agritrust.service;

import com.agritrust.dto.CreateCertificateDto;
import com.agritrust.entity.CertificateEntity;
import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;

public interface CertificateWritable{
	public void revoke(String certificateNumber);
	CertificateEntity addCertificate(CreateCertificateDto dto, ProductBatchEntity product, UserEntity auditor);
}
