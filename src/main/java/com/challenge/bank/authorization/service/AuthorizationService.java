package com.challenge.bank.authorization.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.challenge.bank.authorization.dtos.AuthorizationDTO;
import com.challenge.bank.exception.UnauthorizedTransferException;
import com.challenge.bank.transfer.entities.Transfer;

@Service
public class AuthorizationService {

	private final RestClient restClient;

	public AuthorizationService(RestClient.Builder builder) {
		this.restClient = builder.baseUrl("https://util.devi.tools/api/v2/authorize").build();
	}
	
	public void authorize(Transfer transfer) {
		 
		ResponseEntity<AuthorizationDTO> response = restClient.get().retrieve().toEntity(AuthorizationDTO.class);
		if(response.getStatusCode().isError() || response.getBody().isAuthorized()) {
			throw new UnauthorizedTransferException("Unauthorized transfer!");
		}
	}
	
	
}
