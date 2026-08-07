package com.challenge.bank.transfer.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.bank.authorization.service.AuthorizationService;
import com.challenge.bank.exception.BusinessRuleException;
import com.challenge.bank.transfer.dtos.TransferRequestDTO;
import com.challenge.bank.transfer.dtos.TransferResponseDTO;
import com.challenge.bank.transfer.entities.Transfer;
import com.challenge.bank.transfer.mapper.TransferMapper;
import com.challenge.bank.transfer.repository.TransferRepository;
import com.challenge.bank.user.entities.User;
import com.challenge.bank.user.role.enums.Role;
import com.challenge.bank.user.service.UserService;

@Service
public class TransferService {
	
	private final TransferRepository transferRepository;
	private final UserService userService;
	private final AuthorizationService authorizationService;
	
	public TransferService(TransferRepository transferRepository, UserService userService, AuthorizationService authorizationService) {
		this.transferRepository = transferRepository;
		this.userService = userService;
		this.authorizationService = authorizationService;
	}
	
	@Transactional
	public TransferResponseDTO effectTransfer(TransferRequestDTO transferDto) {
		User sender = userService.findUserById(transferDto.getSenderId());
		User receiver = userService.findUserById(transferDto.getReceiverId());
		Transfer transfer = convertTransferRequestDtoToEntity(transferDto, sender, receiver);
		
		validateTransferConditions(transfer.getSender(), transfer.getValueTransfer());
		Transfer newTransfer = transferRepository.save(transfer);
		sender.getWallet().debit(newTransfer.getValueTransfer());
		receiver.getWallet().credit(newTransfer.getValueTransfer());
		userService.updateUser(sender);
		userService.updateUser(receiver);
		
		authorizationService.authorize(newTransfer);
		//return newTransfer
		
		return createTransferResponseDto(transfer);
	}
	
	private void validateTransferConditions(User sender, BigDecimal amount) {
		if(sender.getUserRole().equals(Role.SELLER)) {
			throw new BusinessRuleException("Sellers are not allowed to make a transfer");
		}
		
		if(sender.getWallet().getBalance().compareTo(amount) < 0) {
			throw new BusinessRuleException("The sender does not have enough balance to make a transfer");
		}
	}
	
	private Transfer convertTransferRequestDtoToEntity(TransferRequestDTO transferDto, User sender, User receiver) {
		return TransferMapper.toEntity(transferDto.getValue(), sender, receiver, transferDto.getTransferDate());
	}
	
	private TransferResponseDTO createTransferResponseDto(Transfer transfer) {
		return TransferMapper.toDto(transfer.getValueTransfer(), 
				transfer.getSender().getFirstName() + " " + transfer.getSender().getLastName(),
				transfer.getReceiver().getFirstName() + " " + transfer.getReceiver().getLastName());
	}
	
}
