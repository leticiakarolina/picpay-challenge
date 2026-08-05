package com.challenge.bank.transfer.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.challenge.bank.user.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="transfer")
public class Transfer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="value_transfer")
	private BigDecimal valueTransfer;
	
	@ManyToOne
	@JoinColumn(name="sender_id", nullable=false)
	private User sender;
	
	@ManyToOne
	@JoinColumn(name="receiver_id", nullable=false)
	private User receiver;
	
	@Column(name="transfer_date")
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate transferDate;
	
	@Column(name="transfer_time")
	@JsonFormat(pattern="HH:mm:ss")
	@CreatedDate
	@CurrentTimestamp
	private LocalTime transferTime;
	
	public Transfer() {
		
	}
	
	public Transfer(BigDecimal valueTransfer, User sender, User receiver, LocalDate transferDate) {
		this.valueTransfer = valueTransfer;
		this.sender = sender;
		this.receiver = receiver;
		this.transferDate = transferDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValueTransfer() {
		return valueTransfer;
	}

	public void setValue(BigDecimal valueTransfer) {
		this.valueTransfer = valueTransfer;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public LocalDate getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}

	public LocalTime getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(LocalTime transferTime) {
		this.transferTime = transferTime;
	}

}
