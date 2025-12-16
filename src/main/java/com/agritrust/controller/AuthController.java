package com.agritrust.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agritrust.dto.JwtTokenDto;
import com.agritrust.dto.LoginDto;
import com.agritrust.entity.UserEntity;
import com.agritrust.service.impl.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<UserEntity> register(@RequestBody UserEntity newUser){
		return ResponseEntity.ok(authService.kaydet(newUser));
	}
	
	@PostMapping("/login")
	public ResponseEntity signin(@RequestBody LoginDto loginDto) {
		JwtTokenDto jwt = authService.giris(loginDto);
		
		if(jwt.getToken() == null) {
			return new ResponseEntity(jwt, HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(jwt);
	}

}
