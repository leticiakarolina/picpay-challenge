package com.challenge.bank.exception;

public class UnauthorizedTransferException extends RuntimeException {
	public UnauthorizedTransferException(String message) {
		super(message);
	}
}
