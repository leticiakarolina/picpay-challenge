package com.challenge.bank.authentication.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.challenge.bank.authentication.model.SecurityUser;
import com.challenge.bank.user.repository.UserRepository;

@Service
public class SecurityUserService implements UserDetailsService {
	
	private UserRepository userRepository;

	public SecurityUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.map(SecurityUser::new)
				.orElseThrow(() -> new BadCredentialsException("E-mail or password invalids."));
	}

}
