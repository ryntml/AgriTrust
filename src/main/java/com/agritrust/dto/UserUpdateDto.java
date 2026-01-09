package com.agritrust.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateDto {

	@NotNull
	@Size(min = 7, max = 30)
	private String username;

	@Size(min = 6, max = 255)
	@NotNull
	private String password;

	@Size(min = 6, max = 30)
	@Email
	private String email;

}
