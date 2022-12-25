package com.mtp.restapipro.service;

import java.util.Optional;

import com.mtp.restapipro.models.ERole;
import com.mtp.restapipro.models.Role;

public interface IRoleService {
	Optional<Role> findByName (ERole name);

}
