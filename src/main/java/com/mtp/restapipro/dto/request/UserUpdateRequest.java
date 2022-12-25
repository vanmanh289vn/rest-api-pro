package com.mtp.restapipro.dto.request;

public class UserUpdateRequest {
	
	private String username;
	private String email;
	private String first_name;
	private String last_name;
	
	public UserUpdateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserUpdateRequest(String username, String email, String first_name, String last_name) {
		super();
		this.username = username;
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	

}
