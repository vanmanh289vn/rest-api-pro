package com.mtp.restapipro.dto.request;

import javax.validation.constraints.NotBlank;

public class TokenRefreshRequest {
	
	@NotBlank
	private String refreshToken;

	public TokenRefreshRequest(String refreshToken) {
		super();
		this.refreshToken = refreshToken;
	}

	public TokenRefreshRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	

}
