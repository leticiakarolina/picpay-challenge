package com.challenge.bank.challenge_bank;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.challenge.bank.authentication.SecurityConfiguration;
import com.challenge.bank.authentication.service.JwtTokenService;
import com.challenge.bank.user.controller.UserController;
import com.challenge.bank.user.dtos.UserDTO;
import com.challenge.bank.user.repository.UserRepository;
import com.challenge.bank.user.role.entities.enums.RoleName;
import com.challenge.bank.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(
	    UserController.class
)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private JwtTokenService tokenService;
	
	@MockBean
	private UserRepository userr;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	private UserDTO user;
	
	@BeforeEach
	public void setup() {
	    user = new UserDTO(
	            "Alice",
	            "Silva",
	            "123.456.789-00",
	            null,
	            "alice.silva@email.com",
	            "senha123",
	            Arrays.asList(RoleName.COMMON),
	            BigDecimal.valueOf(500)
	        );
	}
	
	@Test
	public void createUserTest() throws JsonProcessingException, Exception {
		doNothing().when(userService).saveUser(user);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))
			)
		.andExpect(status().isCreated())
		.andExpect(content().string("User was registered with success"));
	}

}
