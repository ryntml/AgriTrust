package com.agritrust.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.agritrust.service.IJwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtilsService implements IJwtUtils {	//JWT üretilen yer

	private final SecretKey secretKey;
    private final long expirationTime;

    public JwtUtilsService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {
        
        this.expirationTime = expiration;
        
        // 1. Decode the Base64 string from YAML into bytes
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        
        // 2. Create the HMAC-SHA key
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

	@Override
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(secretKey)
				.compact();
	}

	// token içinde kullandığım bilgilerden veri çekmemi sağlayan standart metod
	private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
		// TODO Auto-generated method stub
		return claimsTFunction.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
	}

	@Override
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {

		final String username = extractUsername(token);

		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}

	@Override
	public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder()
				.claims(claims)
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(secretKey).compact();
	}

}
