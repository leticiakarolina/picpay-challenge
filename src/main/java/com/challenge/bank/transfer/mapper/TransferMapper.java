package com.challenge.bank.transfer.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.challenge.bank.transfer.dtos.TransferRequestDTO;
import com.challenge.bank.transfer.dtos.TransferResponseDTO;
import com.challenge.bank.transfer.entities.Transfer;
import com.challenge.bank.user.entities.User;

public class TransferMapper {

	public static TransferResponseDTO toDto(BigDecimal valueTransfer, String senderFullName, String receiverFullName) {
		return new TransferResponseDTO(
			valueTransfer, senderFullName, receiverFullName);
	}
	
	public static Transfer toEntity(BigDecimal value, User sender, User receiver, LocalDate date) {
		return new Transfer(value, sender, receiver, date);
	}
}
