package com.mtp.restapipro.dto.response;

import java.util.List;

public class UserResponse {
	
	private String _id;
	
	private String first_name;
	
	private String last_name;
	
	private String email;
	
	private String avatar;
	
	private List<String> roles;
	
	public UserResponse(String _id, String first_name, String last_name, String email, String avatar, List<String> roles) {
		super();
		this._id = _id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.avatar = avatar;
		this.roles = roles;
	}

	public UserResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
