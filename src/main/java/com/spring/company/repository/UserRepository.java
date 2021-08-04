package com.spring.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.company.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

}
