package com.challenge.bank.authentication.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.challenge.bank.authentication.dtos.JwtTokenDTO;
import com.challenge.bank.authentication.dtos.LoginUserDTO;
import com.challenge.bank.authentication.model.SecurityUser;
import com.challenge.bank.user.repository.UserRepository;

@Service
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenService tokenService;
	
	public AuthenticationService(AuthenticationManager authenticationManager, JwtTokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}
	
	public JwtTokenDTO authenticateUser(LoginUserDTO loginDto) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
		
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
		
		return new JwtTokenDTO(tokenService.generateToken(userDetails));
	}
	
}
