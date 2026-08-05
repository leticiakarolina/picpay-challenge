package com.challenge.bank.transfer.dtos;

import java.math.BigDecimal;

public class TransferResponseDTO {

	private BigDecimal value;
	
	private String fullNamePayer;
	
	private String fullNamePayee;

	public TransferResponseDTO(BigDecimal value, String fullNamePayer, String fullNamePayee) {
		this.value = value;
		this.fullNamePayer = fullNamePayer;
		this.fullNamePayee = fullNamePayee;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getFullNamePayer() {
		return fullNamePayer;
	}

	public void setFullNamePayer(String fullNamePayer) {
		this.fullNamePayer = fullNamePayer;
	}

	public String getFullNamePayee() {
		return fullNamePayee;
	}

	public void setFullNamePayee(String fullNamePayee) {
		this.fullNamePayee = fullNamePayee;
	}
	
}
