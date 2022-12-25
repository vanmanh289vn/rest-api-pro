package com.mtp.restapipro.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtp.restapipro.models.User;

public class UserPrinciple implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private String email;
	@JsonIgnore
	private String password;
	
	// add them
	private String first_name;
	private String last_name;
	private String avatar;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserPrinciple(Long id, String username, String email, String password,
			String first_name, String last_name, String avatar,
			Collection<? extends GrantedAuthority> authorities){
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.avatar = avatar;
		this.authorities = authorities;
	}
	
	public static UserPrinciple build(User user){
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
		return new UserPrinciple(user.getId(), user.getUsername()
				, user.getEmail(), user.getPassword(), user.getFirst_name(), user.getLast_name(), user.getAvatar(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public Long getId(){
		return id;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getFirstName() {
		return first_name;
	}
	
	public String getLastName() {
		return last_name;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserPrinciple user = (UserPrinciple) o;
		return Objects.equals(id, user.id);
					
	}

}
