package com.challenge.bank.authentication;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.challenge.bank.authentication.model.SecurityUser;
import com.challenge.bank.authentication.service.JwtTokenService;
import com.challenge.bank.user.entities.User;
import com.challenge.bank.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtTokenService tokenService;
	private final UserRepository userRepository;

	public UserAuthenticationFilter(JwtTokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recoveryToken(request);
		
		if(token != null ) {
			String subject = tokenService.validateToken(token);
			User user = userRepository.findByEmail(subject).get();
			SecurityUser securityUser = new SecurityUser(user);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser.getUsername(), null, securityUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} 
		
		filterChain.doFilter(request, response);
	}
	
	private String recoveryToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		
		if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
		
        return null;
	}

}
