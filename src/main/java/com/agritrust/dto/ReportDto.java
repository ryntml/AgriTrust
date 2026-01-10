package com.agritrust.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReportDto {

	private String batchCode;

	private String name;
	private String surname;

	@Size(max = 11)
	private String citizenId;

	@NotBlank
	private String reason;
}
