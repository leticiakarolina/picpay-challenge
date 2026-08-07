package com.challenge.bank.challenge_bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.challenge.bank.exception.BusinessRuleException;
import com.challenge.bank.user.dtos.UserDTO;
import com.challenge.bank.user.entities.User;
import com.challenge.bank.user.repository.UserRepository;
import com.challenge.bank.user.role.enums.RoleName;
import com.challenge.bank.user.role.repository.RoleRepository;
import com.challenge.bank.user.service.UserService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserService userService;
	
	private UserDTO userDTO;
	private User user;
	
	@Test
	public void createUserTest() throws Exception {
		userDTO = new UserDTO(
				"Sabrina",
				"Carpenter",
				"123.456.789-00",
	            null,
	            "sabrina.carpenter@gmail.com",
				"senha123",
				Arrays.asList(RoleName.COMMON),
				BigDecimal.valueOf(500));
			
		userService.saveUser(userDTO);
		
		verify(userRepository, times(1)).save(any(User.class));
	}
	
	@Test
	public void validateUserCpf() {
		userDTO = new UserDTO(
				"Sabrina",
				"Carpenter",
				"123.456.789-11",
	            null,
	            "sabrina.carpenter@gmail.com",
				"senha123",
				Arrays.asList(RoleName.COMMON),
				BigDecimal.valueOf(500));
			
		user = new User();
		user.setCpf("123.456.789-11");
		
		when(userRepository.findByEmailOrCnpjOrCpf(userDTO.email(), userDTO.cnpj(), userDTO.cpf())).thenReturn(Optional.of(user));
		
		BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> userService.saveUser(userDTO));
		
		assertEquals("There is already a user registered with this CPF.", exception.getMessage());
		
		verify(userRepository, never()).save(any(User.class));
	}
	
	@Test
	public void validateUserEmail() {
		userDTO = new UserDTO(
				"Sabrina",
				"Carpenter",
				"123.456.789-58",
	            null,
	            "sabrina.carpenter@gmail.com",
				"senha123",
				Arrays.asList(RoleName.COMMON),
				BigDecimal.valueOf(500));
			
		user = new User();
		user.setEmail("sabrina.carpenter@gmail.com");
		
		when(userRepository.findByEmailOrCnpjOrCpf(userDTO.email(), userDTO.cnpj(), userDTO.cpf())).thenReturn(Optional.of(user));
		
		BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> userService.saveUser(userDTO));
		
		assertEquals("There is already a user registered with this e-mail.", exception.getMessage());
		
		verify(userRepository, never()).save(any(User.class));
	}
	
	@Test
	public void validateUserCNPJ() {
		userDTO = new UserDTO(
				"Sabrina",
				"Carpenter",
				"123.456.789-58",
	            "12.123.456/5555-48",
	            "sabrina.carpenter@gmail.com",
				"senha123",
				Arrays.asList(RoleName.COMMON),
				BigDecimal.valueOf(500));
			
		user = new User();
		user.setCnpj("12.123.456/5555-48");
		
		when(userRepository.findByEmailOrCnpjOrCpf(userDTO.email(), userDTO.cnpj(), userDTO.cpf())).thenReturn(Optional.of(user));
		
		BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> userService.saveUser(userDTO));
		
		assertEquals("There is already a user registered with this CNPJ.", exception.getMessage());
		
		verify(userRepository, never()).save(any(User.class));
	}
}
