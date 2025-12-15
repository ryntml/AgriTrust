package com.agritrust.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User{
	@Column(unique=true)
	private long admin_id;
	

}
