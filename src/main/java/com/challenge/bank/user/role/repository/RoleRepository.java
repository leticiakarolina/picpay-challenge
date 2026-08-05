package com.challenge.bank.user.role.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.bank.user.role.entities.Role;
import com.challenge.bank.user.role.entities.enums.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	List<Role> findByNameIn(List<RoleName> name);
}
