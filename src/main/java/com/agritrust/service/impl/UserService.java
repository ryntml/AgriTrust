package com.agritrust.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agritrust.entity.UserEntity;
import com.agritrust.enums.Roles;
import com.agritrust.repos.UserRepository;
import com.agritrust.service.UserReadable;
import com.agritrust.service.UserWritable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService,UserReadable,UserWritable{

	private final UserRepository userRepo;
	private final PasswordEncoder encoder;
	private final ModelMapper modelMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
	}

	@Override
	public List<UserEntity> getList() {
		return userRepo.findByDeletedFalse();
	}

	@Override
	public UserEntity getById(Integer id) {
		return userRepo.findByIdAndDeletedFalse(id).orElseThrow(()-> new IllegalArgumentException());
	}

	@Override
	public void add(UserEntity entity) {
		userRepo.save(entity);
		
	}

	@Override
	public void change(Integer id, UserEntity entity) {
		UserEntity userToUpdate = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());

		modelMapper.map(entity, userToUpdate);
		userRepo.save(userToUpdate);
		
	}

	@Override
	public void remove(Integer id) {
		UserEntity userToDelete = this.getById(id);
		userToDelete.setDeleted(true);
		userRepo.save(userToDelete);	
	}

    public List<UserEntity> findUsersByRoles(Roles role) {//enum nasıl gönderiliyor?
        return userRepo.findAllByRole(role);
    }
    
    public UserEntity findUserByUsername(String username) {
    	return userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
    }
}
