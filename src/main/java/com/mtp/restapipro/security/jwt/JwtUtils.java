package com.mtp.restapipro.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.mtp.restapipro.security.services.UserPrinciple;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${mtp.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${mtp.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	
	
	// ham nay se dc goi luc login
	public String generateJwtToken(UserPrinciple userPrinciple){
		return generateTokenFromUsername(userPrinciple.getUsername());
	}
	
	public String generateTokenFromUsername(String username){
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	// Get username form jwt truyen vao tren header
	
	// Hàm này giúp ta truy ngược ra username từ token gửi đến
	// Khi có username ta sẽ nhờ Spring lấy ra được UserDetails (có chứa tất cả thông tin authorities)
	// giúp phân quyền
	public String getUserNameFromJwtToken(String token){
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String authToken){
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e){
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e){
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e){
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e){
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

}
