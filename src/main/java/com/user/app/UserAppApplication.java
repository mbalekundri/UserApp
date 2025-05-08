package com.user.app;

import com.user.app.dto.UserDTORequest;
import com.user.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserAppApplication implements CommandLineRunner {

	@Autowired
	private IUserService userService;

	public static void main(String[] args) {
		SpringApplication.run(UserAppApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		UserDTORequest userDTORequest = new UserDTORequest(
				null,
				"admin@gmail.com",
				"Admin Controller",
				"112233",
				"Admin",
				8877665544L,
				"Address Line 1",
				"Address Line 2",
				"Pune",
				""
		);
		userService.createUser(userDTORequest);

		userDTORequest = new UserDTORequest(
				null,
				"rohit@gmail.com",
				"Rohit Balekundri",
				"332211",
				"User",
				7766558844L,
				"Address Line 1",
				"Address Line 2",
				"Pune",
				""
		);
		userService.createUser(userDTORequest);
	}
}
