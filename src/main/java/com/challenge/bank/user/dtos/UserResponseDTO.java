package com.challenge.bank.user.dtos;

import java.util.List;

import com.challenge.bank.user.role.entities.Role;

public record UserResponseDTO(
	String firstName,
	String lastName,
	String cpf,
	String cnpj,
	String email,
	List<Role> roles) {

}
