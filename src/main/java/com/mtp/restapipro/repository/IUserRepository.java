package com.mtp.restapipro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mtp.restapipro.models.User;

// de cau truc chuan va chinh xac hon ta nen tao them cac package : 
// service, serviceImpl, irepository, repository

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername (String username);
	
	Boolean existsByUsername (String username);
	
	Boolean existsByEmail (String email);
	
	List<User> findByEmailContaining(String email);
	
	Optional<User> findById (Long id);
	
	Page<User> findByEmailContaining(String email, Pageable pageable);
	
	List<User> findByEmailContaining(String email, Sort sort);
	
	Page<User> findAll(Pageable pageable);
	
}
