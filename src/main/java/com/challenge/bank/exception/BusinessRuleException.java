package com.challenge.bank.exception;

public class BusinessRuleException extends RuntimeException {

	private static final long serialVersionUID = -7585361548708459932L;

	public BusinessRuleException(String message) {
		super(message);
	}
}
