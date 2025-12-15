package com.agritrust.dto;
import lombok.Data;

@Data
public class JwtTokenDto {
	private String token;
	private String refreshToken;

}
