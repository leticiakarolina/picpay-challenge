package com.challenge.bank.transfer.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.bank.exception.BusinessRuleException;
import com.challenge.bank.transfer.dtos.TransferRequestDTO;
import com.challenge.bank.transfer.dtos.TransferResponseDTO;
import com.challenge.bank.transfer.entities.Transfer;
import com.challenge.bank.transfer.mapper.TransferMapper;
import com.challenge.bank.transfer.repository.TransferRepository;
import com.challenge.bank.user.entities.User;
import com.challenge.bank.user.role.entities.enums.RoleName;
import com.challenge.bank.user.service.UserService;

@Service
public class TransferService {
	
	private final TransferRepository transferRepository;
	private final UserService userService;
	
	public TransferService(TransferRepository transferRepository, UserService userService) {
		this.transferRepository = transferRepository;
		this.userService = userService;
	}
	
	@Transactional
	public TransferResponseDTO effectTransfer(TransferRequestDTO transferDto) {
		User sender = userService.findUserById(transferDto.getSenderId());
		User receiver = userService.findUserById(transferDto.getReceiverId());
		Transfer transfer = convertTransferRequestDtoToEntity(transferDto, sender, receiver);
		
		validateTransferConditions(transfer.getSender(), transfer.getValueTransfer());
		Transfer transferDone = transferRepository.save(transfer);
		updateSenderAccount(transferDone.getSender(), transferDone.getValueTransfer());
		updateReceiverAccount(transferDone.getReceiver(), transferDone.getValueTransfer());
		
		return createTransferResponseDto(transfer);
	}
	
	private void validateTransferConditions(User sender, BigDecimal amount) {
		if(sender.getUserRoles().stream().anyMatch(role -> role.getName().equals(RoleName.SELLER))) {
			throw new BusinessRuleException("Sellers are not allowed to make a transfer");
		}
		
		if(sender.getAmount().compareTo(amount) < 0) {
			throw new BusinessRuleException("The sender does not have enough balance to make a transfer");
		}
	}

	private void updateSenderAccount(User sender, BigDecimal valueTransfer) {
		sender.setAmount(sender.getAmount().subtract(valueTransfer));
		userService.updateUser(sender);
	}
	
	private void updateReceiverAccount(User receiver, BigDecimal valueTransfer) {
		receiver.setAmount(receiver.getAmount().add(valueTransfer));
		userService.updateUser(receiver);
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
