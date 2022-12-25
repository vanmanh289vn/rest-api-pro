package com.mtp.restapipro.service;

import java.util.Optional;

import com.mtp.restapipro.models.RefreshToken;

public interface IRefreshTokenService {
	
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
	
	RefreshToken createRefreshToken(Long userId);
	
	RefreshToken verifyExpiration(RefreshToken refreshToken);
	
	int deleteByUserId(Long userId);

}
