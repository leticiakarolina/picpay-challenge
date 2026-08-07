//package com.challenge.bank.challenge_bank;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoInteractions;
//import static org.mockito.Mockito.when;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.challenge.bank.exception.BusinessRuleException;
//import com.challenge.bank.transfer.dtos.TransferRequestDTO;
//import com.challenge.bank.transfer.dtos.TransferResponseDTO;
//import com.challenge.bank.transfer.entities.Transfer;
//import com.challenge.bank.transfer.repository.TransferRepository;
//import com.challenge.bank.transfer.service.TransferService;
//import com.challenge.bank.user.entities.User;
//import com.challenge.bank.user.role.entities.Role;
//import com.challenge.bank.user.role.enums.RoleName;
//import com.challenge.bank.user.service.UserService;
//
//@ExtendWith(MockitoExtension.class)
//public class TransferServiceTest {
//	
//	@Mock
//	private TransferRepository transferRepository;
//	
//	@Mock
//	private UserService userService;
//	
//	@InjectMocks	
//	private TransferService transferService;
//	
//	private User sender;
//	private User receiver;
//			
////	@BeforeEach
////	public void setup() {
////		sender = new User();
////		sender.setId(1L);
////		sender.setFirstName("Alice");
////		sender.setLastName("Silva");
////		sender.setCpf("123.456.789-00");
////		sender.setCnpj(null);
////		sender.setEmail("alice.silva@email.com");
////		sender.setPassword("senha123");
////		sender.setUserType(UserType.COMMON);
////		sender.setAmount(BigDecimal.valueOf(500));
////				
////		receiver = new User();
////		receiver.setId(2L);
////		receiver.setFirstName("Bob");
////		receiver.setLastName("Souza");
////		receiver.setCpf("987.654.321-00");
////		receiver.setCnpj(null);
////		receiver.setEmail("bob.souza@email.com");
////		receiver.setPassword("senha456");
////		receiver.setUserType(UserType.SELLER);
////		receiver.setAmount(BigDecimal.valueOf(300));
////	}
//	
//	@Test
//	public void transactionTest() {
//		TransferRequestDTO transferRequest = new TransferRequestDTO(
//				BigDecimal.valueOf(100.00), 1L, 2L, LocalDate.now());
//		
//		User sender = new User();
//		sender.setUserRoles(List.of(new Role(RoleName.COMMON)));
//		sender.setId(1L);
//		sender.setAmount(BigDecimal.valueOf(500.00));
//			
//		User receiver = new User();
//		receiver.setId(2L);
//		receiver.setAmount(BigDecimal.valueOf(250.00));
//		
//		when(userService.findUserById(1L)).thenReturn(sender);
//		when(userService.findUserById(2L)).thenReturn(receiver);
//		
//		when(transferRepository.save(any(Transfer.class)))
//	    .thenAnswer(invocation -> invocation.getArgument(0));
//		
//		transferService.effectTransfer(transferRequest);
//		
//		ArgumentCaptor<Transfer> captor = ArgumentCaptor.forClass(Transfer.class);
//
//		verify(transferRepository).save(captor.capture());
//
//		Transfer savedTransfer = captor.getValue();
//		
//		assertEquals(BigDecimal.valueOf(100.00), savedTransfer.getValueTransfer());
//		assertEquals(BigDecimal.valueOf(400.00), savedTransfer.getSender().getAmount());
//		assertEquals(BigDecimal.valueOf(350.00), savedTransfer.getReceiver().getAmount());
//		
//		verify(userService, times(1)).findUserById(1L);
//		verify(userService, times(1)).findUserById(2L);
//		verify(transferRepository, times(1)).save(any(Transfer.class));
//	}
//	
//	@Test
//	public void userIsSellerTest() {
//		TransferRequestDTO transferRequest = new TransferRequestDTO(
//			BigDecimal.valueOf(100.00), 1L, 2L, LocalDate.now());
//		
//		User sender = new User();
//		sender.setUserRoles(List.of(new Role(RoleName.SELLER)));
//		sender.setId(1L);
//		
//		User receiver = new User();
//		receiver.setId(2L);
//		
//		when(userService.findUserById(1L)).thenReturn(sender);
//		when(userService.findUserById(2L)).thenReturn(receiver);
//		
//		BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> transferService.effectTransfer(transferRequest));
//		
//		assertEquals("Sellers are not allowed to make a transfer", exception.getMessage());
//		
//		verify(userService).findUserById(1L);
//		verify(userService).findUserById(2L);
//		verifyNoInteractions(transferRepository);
//	}
//	
//	@Test
//	public void notEnoughBalance() {
//		TransferRequestDTO transferRequest = new TransferRequestDTO(
//				BigDecimal.valueOf(100.00), 1L, 2L, LocalDate.now());
//			
//		User sender = new User();
//		sender.setUserRoles(List.of(new Role(RoleName.COMMON)));
//		sender.setId(1L);
//		sender.setAmount(BigDecimal.valueOf(50.00));
//			
//		User receiver = new User();
//		receiver.setId(2L);
//		
//		when(userService.findUserById(1L)).thenReturn(sender);
//		when(userService.findUserById(2L)).thenReturn(receiver);
//		
//		BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> transferService.effectTransfer(transferRequest));
//		
//		assertEquals("The sender does not have enough balance to make a transfer", exception.getMessage());
//		
//		verify(userService).findUserById(1L);
//		verify(userService).findUserById(2L);
//		verifyNoInteractions(transferRepository);
//	}
//
//}
