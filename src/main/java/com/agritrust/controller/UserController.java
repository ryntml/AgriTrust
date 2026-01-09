package com.agritrust.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.agritrust.dto.UserUpdateDto;
import com.agritrust.entity.UserEntity;
import com.agritrust.service.UserReadable;
import com.agritrust.service.UserWritable;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserReadable userReadableService;
	private final UserWritable userWritableService;
	private final ModelMapper modelMapper;

	@GetMapping({ "/users", "/users/" })
	public ResponseEntity<List<UserEntity>> getAllUsers() {

		List<UserEntity> users = userReadableService.getList();

		if (users.isEmpty()) {
			return ResponseEntity.noContent().build(); // dönüş başarılı ama içerik yok
		}

		return ResponseEntity.ok(users);
	}

	@PostMapping("/users/{id}") // username password email değişebilir
	public void updateUser(@PathVariable("id") Integer id, @Valid @RequestBody UserUpdateDto userDto) {

		UserEntity user = new UserEntity();
		modelMapper.map(userDto, user);
		user.setId(id);

		userWritableService.change(id, user);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserEntity> getUser(@PathVariable("id") Integer id) {

		UserEntity user = userReadableService.getById(id);

		if (user.isDeleted()) {
			return ResponseEntity.noContent().build(); // dönüş başarılı ama içerik yok
		}

		return ResponseEntity.ok(user);
	}

	@GetMapping("/admin/users/{id}")
	public ResponseEntity<UserEntity> deleteUser(@PathVariable Integer id) {
		userWritableService.remove(id);

		return ResponseEntity.ok(userReadableService.getById(id));
	}

}
