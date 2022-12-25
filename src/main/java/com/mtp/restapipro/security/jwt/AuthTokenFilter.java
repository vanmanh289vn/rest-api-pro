package com.mtp.restapipro.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mtp.restapipro.security.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	
	// Ham doFilterInternal nay rat quan trong, no se duoc goi den ngay sau khi cap username, password de thuc hien login
	// 
	
	// Thực tế thì hàm này được gọi đến ngay sau khi gửi request bất kỳ (login, access link khác ...)
	// Vì trong cấu hình class config yêu cầu nó phải xử lý trước tiên : 
	// http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			
			// Tu JWT nhap vao trong header, ta se truy xuat duoc username
			// Tu username -> userDetails (id, username, ..., authorities) --> authentication --> set Auth vao Context de su dung
			
			
			String jwt = parseJwt(request);
			if(jwt != null && jwtUtils.validateJwtToken(jwt)){
				
				// Khi thuc hien login, thong tin username se dc lay tu chinh token duoc tao ra
				
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e.getMessage());
		}
		filterChain.doFilter(request, response);
		
	}
	
	// function get Token duoc truyen vao tu header tren Postman
	private String parseJwt(HttpServletRequest request){
		//String headerAuth = request.getHeader("Authorization");
//		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
//			return headerAuth.substring(7, headerAuth.length());
//		}
		
		String headerAuth = request.getHeader("x-auth-token");
		if(StringUtils.hasText(headerAuth)){
			return headerAuth;
		}
		
		
		return null;
	}

}
