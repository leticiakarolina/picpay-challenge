package com.challenge.bank.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.bank.exception.BusinessRuleException;
import com.challenge.bank.user.dtos.UserDTO;
import com.challenge.bank.user.dtos.UserResponseDTO;
import com.challenge.bank.user.entities.User;
import com.challenge.bank.user.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with ID " + id + " was not found"));
	}
	
	public User saveUser(UserDTO userDTO) {
		validateUserRegistrationByIdentities(userDTO);
		User user = createUser(userDTO);
		return userRepository.save(user);
	}
	
	public void updateUser(User user) {
		userRepository.save(user);
	}
	
	private void validateUserRegistrationByIdentities(UserDTO userDTO) {
		Optional<User> userValidation = userRepository.findByEmailOrCnpjOrCpf(userDTO.email(), userDTO.cnpj(), userDTO.cpf());
		
		if(userValidation.isEmpty()) {
			return;
		}
		
		if(userValidation.get().getEmail() != null && userValidation.get().getEmail().equals(userDTO.email())) {
			throw new BusinessRuleException("There is already a user registered with this e-mail.");
		}
		
		if(userValidation.get().getCnpj() != null && userValidation.get().getCnpj().equals(userDTO.cnpj())) {
			throw new BusinessRuleException("There is already a user registered with this CNPJ.");
		}
		
		if(userValidation.get().getCpf() != null && userValidation.get().getCpf().equals(userDTO.cpf())) {
			throw new BusinessRuleException("There is already a user registered with this CPF.");
		}

	}
	
	private User createUser(UserDTO userDTO) {
		User user = new User();
		user.setFirstName(userDTO.firstName());
		user.setLastName(userDTO.lastName());
		user.setCpf(userDTO.cpf());
		user.setCnpj(userDTO.cnpj());
		user.setEmail(userDTO.email());
		user.setPassword(userDTO.password());
		user.setUserRole(userDTO.role());
		
		return user;
	}
	
	private UserResponseDTO createUserResponseDTO(User user) {
		UserResponseDTO userDto = new UserResponseDTO(
			user.getFirstName(), 
			user.getLastName(), 
			user.getCpf(), 
			user.getCnpj(), 
			user.getEmail(), 
			user.getUserRole());
		
		return userDto;
	}
}
