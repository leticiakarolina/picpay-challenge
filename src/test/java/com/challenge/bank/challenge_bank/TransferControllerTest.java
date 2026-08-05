package com.challenge.bank.challenge_bank;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.challenge.bank.authentication.service.JwtTokenService;
import com.challenge.bank.transfer.controller.TransferController;
import com.challenge.bank.transfer.dtos.TransferRequestDTO;
import com.challenge.bank.transfer.dtos.TransferResponseDTO;
import com.challenge.bank.transfer.service.TransferService;
import com.challenge.bank.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {TransferController.class})
@AutoConfigureMockMvc(addFilters = false)
public class TransferControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TransferService transferService;
	
	@MockBean
	private JwtTokenService tokenService;
	
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
    private ObjectMapper objectMapper;

	private TransferRequestDTO requestDto;
	private TransferResponseDTO transferResponse;

	@BeforeEach
	public void setup() {
		requestDto = new TransferRequestDTO(new BigDecimal(100) , 4L, 3L, LocalDate.now());
	    transferResponse = new TransferResponseDTO(BigDecimal.valueOf(100L), "Alice Silva", "Bob Souza");       
	}
	
	@Test
	public void effectTransferTest() throws JsonProcessingException, Exception {		
		when(transferService.effectTransfer(any(TransferRequestDTO.class)))
        .thenReturn(transferResponse);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/transfer")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(requestDto))
		)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.value", is(100)))
		.andExpect(jsonPath("$.fullNamePayer", is("Alice Silva")))
		.andExpect(jsonPath("$.fullNamePayee", is("Bob Souza")));
		
	}
}
