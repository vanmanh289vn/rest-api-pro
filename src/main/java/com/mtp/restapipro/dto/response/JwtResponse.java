package com.mtp.restapipro.dto.response;

public class JwtResponse {

	private String token;
	private String refreshToken;
	// private String type = "Bearer";
	// private Long id;
	// private String username;
	// private String email;
	// private List<String> roles;

	private UserResponse user;

	// public JwtResponse(String token, Long id, String username, String email,
	// List<String> roles) {
	// super();
	// this.token = token;
	// this.id = id;
	// this.username = username;
	// this.email = email;
	// this.roles = roles;
	// }

	public JwtResponse(String token, String refreshToken, UserResponse user) {
		super();
		this.token = token;
		this.refreshToken = refreshToken;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	// public Long getId() {
	// return id;
	// }
	// public void setId(Long id) {
	// this.id = id;
	// }
	// public String getUsername() {
	// return username;
	// }
	// public void setUsername(String username) {
	// this.username = username;
	// }
	// public String getEmail() {
	// return email;
	// }
	// public void setEmail(String email) {
	// this.email = email;
	// }
	// public List<String> getRoles() {
	// return roles;
	// }

	// public String getType() {
	// return type;
	// }
	// public void setType(String type) {
	// this.type = type;
	// }

}
