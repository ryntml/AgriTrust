package com.agritrust.service;

import com.agritrust.dto.JwtTokenDto;
import com.agritrust.dto.LoginDto;
import com.agritrust.entity.UserEntity;

public interface IAuthService {
	JwtTokenDto giris(LoginDto loginDTO);
	UserEntity kaydet(UserEntity kayitDTO);
}
