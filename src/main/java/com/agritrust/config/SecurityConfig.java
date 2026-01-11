package com.agritrust.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.agritrust.service.impl.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private JwtAuthFilter jwtAuthFilter;
	private UserService userService;

	public SecurityConfig(@Lazy JwtAuthFilter jwtAuthFilter, @Lazy UserService userService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userService = userService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(Customizer.withDefaults())
				.logout((logout) -> logout.logoutUrl("/auth/logout"))
				.authorizeHttpRequests(r -> r
						// === HERKESE AÇIK ENDPOINT'LER ===
						.requestMatchers("/auth/**").permitAll() // Login, Register, Logout
						.requestMatchers("/report/**").permitAll() // Şikayet bildirimi herkese açık
						.requestMatchers("/error").permitAll() // Hata sayfaları

						// === ADMIN - TÜM YETKİLER ===
						.requestMatchers("/admin/**").hasRole("ADMIN")

						// === PRODUCER - Ürün ekleme ve Processing Event ===
						.requestMatchers(HttpMethod.POST, "/product").hasRole("PRODUCER")
						.requestMatchers("/product/trans/**").hasAnyRole("PRODUCER", "ADMIN")

						// === DISTRIBUTOR - Lojistik/Transfer Event ===
						.requestMatchers("/product/logistics/**").hasAnyRole("DISTRIBUTOR", "ADMIN")

						// === AUDITOR - Sertifika işlemleri ===
						.requestMatchers("/product/cert/**").hasAnyRole("AUDITOR", "ADMIN")

						// === DİĞER TÜM İSTEKLER - Sadece Login Gerekli ===
						.anyRequest().authenticated())
				.sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
