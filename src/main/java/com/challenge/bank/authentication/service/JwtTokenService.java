package com.challenge.bank.authentication.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.challenge.bank.authentication.model.SecurityUser;

@Service
public class JwtTokenService {

	@Value("${jwt.secret.key}")
	private String secretKey;
	
	private static final String ISSUER = "challenge-api";
	
	@Value("${application.timezone}")
	private ZoneId zoneId;
	
	public String generateToken(SecurityUser user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			
			return JWT.create()
					.withIssuer(ISSUER)
					.withIssuedAt(creationDate())
					.withExpiresAt(expirationDate())
					.withSubject(user.getUsername())
					.sign(algorithm);
		} catch(JWTCreationException ex) {
			throw new RuntimeException("Error while generating token", ex);
		}
	}
	
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			
			return JWT.require(algorithm)
					.withIssuer(ISSUER)
					.build()
					.verify(token)
					.getSubject();
		} catch(JWTVerificationException ex) {
			throw new RuntimeException("Error while validating token", ex);
		}
		
	}
	
    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }
    
    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(4).toInstant();
    }
}
