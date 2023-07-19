package com.chatapp.DTO;



import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {	
	
	private int id;	
	private String username;	
	private String password;	
	private String image;	
	private Timestamp joinDate;
	
	
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", password=" + password + ", image=" + image
				+ ", joinDate=" + joinDate + "]";
	}
	
	
}
