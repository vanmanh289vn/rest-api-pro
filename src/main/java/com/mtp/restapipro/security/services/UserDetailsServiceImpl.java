package com.mtp.restapipro.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mtp.restapipro.models.User;
import com.mtp.restapipro.repository.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	IUserRepository userRepository;

	@Override
	@Transactional // dung @Transactional vi dang thuc thi lien quan den database
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username));
		// neu tim thay du lieu thi build ra mot doi tuong user
		return UserPrinciple.build(user);
	}

}
