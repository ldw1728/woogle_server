package com.project.woogle;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.config.JwtTokenProvider;
import com.project.model.User;
import com.project.service.UserDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder passWordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/join")
	public Long join(@RequestBody Map<String, String> user) {
		return userDetailsService.saveUser(User.builder()
				.email(user.get("email"))
				.password(passWordEncoder.encode(user.get("password")))
				.roles(Collections.singletonList("ROLE_USER"))
				.build());
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Map<String, String> user) {
		System.out.println(user.toString());
		User member = userDetailsService.getUser(user.get("email"))
				.orElseThrow(()->{
					return new IllegalArgumentException("가입되지 않은 E-MAIL입니다");
					});
		if(!passWordEncoder.matches(user.get("password"), member.getPassword())) {
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
		
		 String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
		 System.out.println(token);
		return token;
			
	}
	
}
