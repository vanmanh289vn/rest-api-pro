package com.mtp.restapipro.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mtp.restapipro.exception.TokenRefreshException;
import com.mtp.restapipro.models.RefreshToken;
import com.mtp.restapipro.repository.IRefreshTokenRepository;
import com.mtp.restapipro.repository.IUserRepository;
import com.mtp.restapipro.service.IRefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {
	
	@Value("${mtp.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;
	
	@Autowired
	private IRefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
		return refreshTokenRepository.findByRefreshToken(refreshToken);
	}

	@Override
	public RefreshToken createRefreshToken(Long userId) {
		
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUser(userRepository.findById(userId).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		
		refreshToken = refreshTokenRepository.save(refreshToken);
		
		return refreshToken;
	}

	@Transactional
	@Override
	public RefreshToken verifyExpiration(RefreshToken refreshToken) {
		if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0){
//			refreshTokenRepository.delete(refreshToken);
			refreshTokenRepository.deleteById(refreshToken.getId());
			throw new TokenRefreshException(refreshToken.getRefreshToken(), "Refresh token was expired. Please make a new signin request");
		}
		return refreshToken;
	}

	@Transactional
	@Override
	public int deleteByUserId(Long userId) {
		return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	}

}
