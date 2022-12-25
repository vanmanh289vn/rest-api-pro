package com.mtp.restapipro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.mtp.restapipro.models.User;

public interface IUserService {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	User save(User user);
	
	List<User> findAll();
	
	List<User> findByEmailContaining (String email);
	
	Optional<User> findById (Long id);
	
	void deleteById (Long id);
	
	void deleteAll();
	
	Page<User> findByEmailContaining(String email, Pageable pageable);
	
	List<User> findByEmailContaining(String email, Sort sort);
	
	Page<User> findAll(Pageable pageable);
	
	void deleteAllUserByIds(List<Long> ids);
	
	

}
