package com.agritrust.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agritrust.entity.UserEntity;
import com.agritrust.enums.Roles;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer>{
	Optional<UserEntity> findByUsername(String username);	
	boolean existsByUsername(String username);
	List<UserEntity> findByDeletedFalse();
	List<UserEntity> findAllByRole(Roles role);
	Optional<UserEntity> findByIdAndDeletedFalse(Integer id);

}
