package com.agritrust.dto;

import com.agritrust.enums.Roles;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

	@Min(10000000000L) // The @Size annotation is intended for Strings, Collections, Maps, or Arrays.
	@Max(99999999999L)
	private Long citizen_id;

	@Size(min = 2, max = 100)
	@NotNull
	private String name;

	@Size(min = 2, max = 150)
	@NotNull
	private String surname;

	@NotNull
	@Size(min = 7, max = 30)
	private String username;

	@Size(min = 6, max = 255)
	@NotNull
	private String password;

	@Size(min = 6, max = 30)
	@Email
	private String email;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Roles role;
	
	private Boolean deleted;
}
