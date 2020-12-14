package com.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.model.User;
import com.project.repo.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Long saveUser(User user) {
		return userRepository.save(user).getId();
	}
	
	public Optional<User> getUser(String username) {
		return userRepository.findByEmail(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(username)
				.orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}

}
