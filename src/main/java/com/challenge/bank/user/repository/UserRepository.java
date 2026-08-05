package com.challenge.bank.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.bank.user.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmailOrCnpjOrCpf(String email, String cnpj, String cpf);
	
	Optional<User> findByEmail(String email);

}
