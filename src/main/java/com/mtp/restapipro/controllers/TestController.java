package com.mtp.restapipro.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@GetMapping("/all")
	public String allAccess(){
		return "Public content..";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
	public String userAccess(){
		return "User content..";
	}
	
	@GetMapping("/pm")
	@PreAuthorize("hasRole('PM')")
	public String modAccess(){
		return "Mod content..";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addminAccess(){
		return "Admin content..";
	}

}
