package com.challenge.bank.user.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.challenge.bank.user.role.entities.enums.RoleName;

public record UserDTO(
	String firstName,
	String lastName,
	String cpf,
	String cnpj,
	String email,
	String password,
	List<RoleName> roles,
	BigDecimal amount) {

}
