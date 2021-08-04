package com.spring.company;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.company.model.User;
import com.spring.company.repository.UserRepository;

@SpringBootApplication
public class CompanyApp {
	
	@Autowired
	UserRepository userRepository;
	
	@PostConstruct
	public void initUsers(){
		List<User> users = new ArrayList<>();
		
		User u1 = new User(101L,"rk","password","rk@gmail.com");
		User u2 = new User(101L,"test","password","test@gmail.com");
		
		users.add(u1);
		users.add(u2);
		
		userRepository.saveAll(users);
		
	}

	public static void main(String[] args) {
		SpringApplication.run(CompanyApp.class, args);
	}

}
