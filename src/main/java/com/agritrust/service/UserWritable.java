package com.agritrust.service;

import com.agritrust.entity.UserEntity;

public interface UserWritable extends Writable<UserEntity,Integer>{
	void change(Integer id, UserEntity entity);
	void remove(Integer id);	//for filtering reasons, state change, no removal
}
