package com.challenge.bank.user.dtos;

import java.math.BigDecimal;

import com.challenge.bank.user.role.enums.Role;

public record UserDTO(
	String firstName,
	String lastName,
	String cpf,
	String cnpj,
	String email,
	String password,
	Role role,
	BigDecimal amount) {

}
