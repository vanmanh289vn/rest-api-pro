package com.mtp.restapipro.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtp.restapipro.dto.request.LoginRequest;
import com.mtp.restapipro.dto.request.SignupRequest;
import com.mtp.restapipro.dto.request.TokenRefreshRequest;
import com.mtp.restapipro.dto.response.JwtResponse;
import com.mtp.restapipro.dto.response.MessageResponse;
import com.mtp.restapipro.dto.response.TokenRefreshResponse;
import com.mtp.restapipro.dto.response.UserResponse;
import com.mtp.restapipro.exception.TokenRefreshException;
import com.mtp.restapipro.models.ERole;
import com.mtp.restapipro.models.RefreshToken;
import com.mtp.restapipro.models.Role;
import com.mtp.restapipro.models.User;
import com.mtp.restapipro.security.jwt.JwtUtils;
import com.mtp.restapipro.security.services.UserPrinciple;
import com.mtp.restapipro.service.IRefreshTokenService;
import com.mtp.restapipro.service.IRoleService;
import com.mtp.restapipro.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IRefreshTokenService refreshTokenService;
	
	@Autowired
	IRoleService roleService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		
		// Sau hàm authenticate này, Spring sẽ tự động gọi đến UserDetailsServiceImpl 
		// để get UserDetails chứa toàn bộ thông tin
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		
		// Chu y luong chay : 
		// Khi dang nhap AuthController --> AuthTokenFilter --> JwtUtils
		// Se debug ky lai luong chay o day de xac dinh chi tiet hon
		
		// UsernamePasswordAuthenticationToken : Tim hieu ky hon class nay
		
		String jwt = jwtUtils.generateJwtToken(userPrinciple);
		
		// Tai sao co the get dc userDetails nhu ben duoi ???
		UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority()).collect(Collectors.toList());
		
		UserResponse user = new UserResponse(userDetails.getId().toString(), userDetails.getFirstName()
				, userDetails.getLastName(), userDetails.getEmail(), userDetails.getAvatar(), roles);
		
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		
		return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getRefreshToken(), user));
	}
	
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request){
		String requestRefreshToken = request.getRefreshToken();
		
		return refreshTokenService.findByRefreshToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
		if(userService.existsByUsername(signupRequest.getUsername())){
			return ResponseEntity.badRequest()
					.body(new MessageResponse("MTP say error : Username is already taken!"));
		}
		
		if(userService.existsByEmail(signupRequest.getEmail())){
			return ResponseEntity.badRequest()
					.body(new MessageResponse("MTP say error : Email is already in use"));
		}
		
		// Create user
		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()), signupRequest.getFirst_name(), signupRequest.getLast_name(), signupRequest.getAvatar());
		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null){
			Role userRole = roleService.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error : Role is not found"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error : Role admin is not found"));
					roles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleService.findByName(ERole.ROLE_PM)
							.orElseThrow(() -> new RuntimeException("Error : Role PM is not found"));
					roles.add(modRole);
					break;

				default:
					Role userRole = roleService.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error : Role user is not found"));
					roles.add(userRole);
					break;
				}
			});
		}
		
		user.setRoles(roles);
		userService.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successful by MTP"));
		
	}

}
