package com.mtp.restapipro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mtp.restapipro.models.User;
import com.mtp.restapipro.repository.IUserRepository;
import com.mtp.restapipro.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	IUserRepository userRepository;

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	@Override
	public List<User> findByEmailContaining(String email) {
		List<User> users = new ArrayList<User>();
		userRepository.findByEmailContaining(email).forEach(users::add);
		return users;
	}

	@Override
	public Optional<User> findById(Long id) {
		
		return userRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	public Page<User> findByEmailContaining(String email, Pageable pageable) {
		
		return userRepository.findByEmailContaining(email, pageable);
	}
	
	@Override
	public List<User> findByEmailContaining(String email, Sort sort) {
		return userRepository.findByEmailContaining(email, sort);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public void deleteAllUserByIds(List<Long> ids) {
		userRepository.deleteAllById(ids);
		
	}
	

}
