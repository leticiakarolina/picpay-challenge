package com.challenge.bank.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.bank.authentication.dtos.JwtTokenDTO;
import com.challenge.bank.authentication.dtos.LoginUserDTO;
import com.challenge.bank.authentication.service.AuthenticationService;

@RestController
@RequestMapping("api")
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/authentication")
	public ResponseEntity<JwtTokenDTO> login(@RequestBody LoginUserDTO loginUser) {
		JwtTokenDTO token = authenticationService.authenticateUser(loginUser);
		return new ResponseEntity<>(token, HttpStatus.OK);
	}

}
