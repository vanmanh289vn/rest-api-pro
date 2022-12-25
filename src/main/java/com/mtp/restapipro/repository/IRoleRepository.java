package com.mtp.restapipro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mtp.restapipro.models.ERole;
import com.mtp.restapipro.models.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName (ERole name);
}
