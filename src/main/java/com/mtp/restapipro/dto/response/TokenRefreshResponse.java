package com.mtp.restapipro.dto.response;

public class TokenRefreshResponse {
	
	private String token;
	private String refreshToken;
	
	public TokenRefreshResponse(String token, String refreshToken) {
		super();
		this.token = token;
		this.refreshToken = refreshToken;
	}
	public TokenRefreshResponse() {
		super();
	}
	public String getToken() {
		return token;
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
	
	

}
