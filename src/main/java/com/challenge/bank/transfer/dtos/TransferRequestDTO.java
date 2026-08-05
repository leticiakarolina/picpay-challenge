package com.challenge.bank.transfer.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TransferRequestDTO {
	
	private BigDecimal value;
	
	private Long senderId;
	
	private Long receiverId;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate transferDate;
	
	public TransferRequestDTO(BigDecimal value, Long senderId, Long receiverId, LocalDate transferDate) {
		this.value = value;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.transferDate = transferDate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public LocalDate getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}

}
