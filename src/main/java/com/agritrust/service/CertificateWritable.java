package com.agritrust.service;

import com.agritrust.entity.CertificateEntity;

public interface CertificateWritable extends Writable<CertificateEntity,Long>{
	public void revoke(String certificateNumber);
}
