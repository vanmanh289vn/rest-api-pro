package com.mtp.restapipro.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtp.restapipro.models.ERole;
import com.mtp.restapipro.models.Role;
import com.mtp.restapipro.repository.IRoleRepository;
import com.mtp.restapipro.service.IRoleService;
@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	IRoleRepository roleRepository;

	@Override
	public Optional<Role> findByName(ERole name) {
		return roleRepository.findByName(name);
	}

}
