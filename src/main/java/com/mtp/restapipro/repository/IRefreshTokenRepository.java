package com.mtp.restapipro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.mtp.restapipro.models.RefreshToken;
import com.mtp.restapipro.models.User;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
	
	// xem lai y nghia va cach su dung anotation @Modifying
	@Modifying
	int deleteByUser(User user);

}
