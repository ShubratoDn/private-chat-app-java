package com.chatapp.entities;

import java.sql.Timestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 200,  nullable = false)
	private String username;
	
	@Column(length = 300,  nullable = false)
	private String password;
	
	@Column(length = 400,  nullable = false)
	private String image;	
	
	
	private Timestamp joinDate;
	

}
