package com.agritrust.service;

public interface Writable <T,ID> {
	void add(T entity);
	//void change(ID id, T entity); moved to user writable as other entities wont be changed
	//void remove(ID id); kayıtlar immutable

}
