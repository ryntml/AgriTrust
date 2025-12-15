package com.agritrust.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
	@Size(min=6, max=30)
	private String username;
	private String password;
}
