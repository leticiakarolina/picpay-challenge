package com.challenge.bank.user.entities;

import java.util.List;

import com.challenge.bank.transfer.entities.Transfer;
import com.challenge.bank.user.role.enums.Role;
import com.challenge.bank.wallet.entities.Wallet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="cpf", unique=true)
	private String cpf;
	
	@Column(name="cnpj")
	private String cnpj;
	
	@Column(name="email", unique=true)
	private String email;
	
	@Column(name="password_auth")
	private String password;
	
	@Column(name="role")
	private Role userRole;
	
	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transfer> transfersReceived;
	
	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transfer> transfersSend;
	
	@OneToOne(mappedBy = "user")
	@PrimaryKeyJoinColumn
	private Wallet wallet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	public List<Transfer> getTransfersReceived() {
		return transfersReceived;
	}

	public void setTransfersReceived(List<Transfer> transfersReceived) {
		this.transfersReceived = transfersReceived;
	}

	public List<Transfer> getTransfersSend() {
		return transfersSend;
	}

	public void setTransfersSend(List<Transfer> transfersSend) {
		this.transfersSend = transfersSend;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

}
