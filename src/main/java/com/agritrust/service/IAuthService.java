package com.agritrust.service;

import com.agritrust.dto.JwtTokenDto;
import com.agritrust.dto.LoginDto;
import com.agritrust.dto.RegisterDto;
import com.agritrust.entity.UserEntity;

public interface IAuthService {
	JwtTokenDto giris(LoginDto loginDTO);
	RegisterDto kaydet(RegisterDto kayitDTO);
}
