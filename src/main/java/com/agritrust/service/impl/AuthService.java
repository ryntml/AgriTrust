package com.agritrust.service.impl;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agritrust.dto.JwtTokenDto;
import com.agritrust.dto.LoginDto;
import com.agritrust.dto.RegisterDto;
import com.agritrust.entity.UserEntity;
import com.agritrust.service.IAuthService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JwtUtilsService jwtUtilsService;
	private final ModelMapper mapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public JwtTokenDto giris(LoginDto loginDto) {
		JwtTokenDto tokenDto = new JwtTokenDto();

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

			UserEntity user = userService.findUserByUsername(loginDto.getUsername());

			String token = jwtUtilsService.generateToken(user);
			String refreshToken = jwtUtilsService.generateRefreshToken(new HashMap<>(), user);

			tokenDto.setToken(token);
			tokenDto.setRefreshToken(refreshToken);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return tokenDto;
	}

	public RegisterDto kaydet(RegisterDto kayitDTO){
		
		UserEntity user = mapper.map(kayitDTO,UserEntity.class);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.add(user);
		
		RegisterDto kullaniciDTO = this.mapper.map(user, RegisterDto.class);
		
		return kullaniciDTO;
		
	}

}
