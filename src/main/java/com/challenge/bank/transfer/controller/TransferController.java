package com.challenge.bank.transfer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.bank.transfer.dtos.TransferRequestDTO;
import com.challenge.bank.transfer.dtos.TransferResponseDTO;
import com.challenge.bank.transfer.services.TransferService;
import com.challenge.bank.user.service.UserService;

@RestController
@RequestMapping("api")
public class TransferController {
	
	private final TransferService transferService;

	public TransferController(TransferService transferService) {
		this.transferService = transferService;
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<TransferResponseDTO> makeTransfer(@RequestBody TransferRequestDTO transferDto) {
		TransferResponseDTO transferResponseDto =  transferService.effectTransfer(transferDto);
		return new ResponseEntity<TransferResponseDTO>(transferResponseDto, HttpStatus.CREATED);
	}
}
