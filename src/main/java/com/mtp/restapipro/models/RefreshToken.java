package com.mtp.restapipro.models;

import java.time.Instant;

import javax.persistence.*;

@Entity(name="refreshtoken")
public class RefreshToken {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name= "user_id", referencedColumnName = "id")
	private User user;
	
	@Column(nullable= false, unique = true)
	private String refreshToken;
	
	@Column(nullable= false)
	private Instant expiryDate;

	public RefreshToken() {
		super();
	}

	public RefreshToken(Long id, User user, String refreshToken, Instant expiryDate) {
		super();
		this.id = id;
		this.user = user;
		this.refreshToken = refreshToken;
		this.expiryDate = expiryDate;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Instant getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}
}
