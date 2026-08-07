package com.challenge.bank.user.dtos;

import com.challenge.bank.user.role.enums.Role;

public record UserResponseDTO(
	String firstName,
	String lastName,
	String cpf,
	String cnpj,
	String email,
	Role role) {

}
