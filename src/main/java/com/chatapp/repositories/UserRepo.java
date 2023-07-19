package com.chatapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	
	List<User> findByUsernameContaining(String partialUsername);
}
