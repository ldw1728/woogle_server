package com.project.config;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	
	private String secretKey = "webfirewood";
	
	private long tokenValidTime = 30 * 60 * 1000L; //��ū��ȿ�ð� 30��
	
	private final UserDetailsService userDetailsService;
	
	//��ü �ʱ�ȭ secretKey�� Base64�� ���ڵ��Ѵ�.
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	//JWT��ū ����
	public String createToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk); //JWT payload �� ����Ǵ� ��������
		claims.put("roles", roles); //������ key/value ������ ����.
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims) //��������
				.setIssuedAt(now) //��ū ���� �ð� ����
				.setExpiration(new Date(now.getTime() + tokenValidTime)) //set Expire Time
				.signWith(SignatureAlgorithm.HS256, secretKey) //����� ��ȣȭ �˰���, signature�� �� secret�� ����, ���� part
				.compact();
	}
	
	//JWT ��ū���� ���� ���� ��ȸ
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	//��ū���� ȸ�� ���� ����
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		
	}
	
	//request�� Header���� token���� �����´�. "X-AUTH-TOKEN" : "TOKEN��"
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("x-auth-token");
	}
	
	//��ū�� ��ȿ�� + �������� Ȯ��
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		}catch(Exception e) {
			return false;
		}
	}
	
	
}
