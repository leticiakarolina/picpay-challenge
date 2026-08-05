package com.challenge.bank.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.bank.transfer.entities.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
