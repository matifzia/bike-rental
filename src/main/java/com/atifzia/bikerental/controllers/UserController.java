package com.atifzia.bikerental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.atifzia.bikerental.dtos.UserRequestDTO;
import com.atifzia.bikerental.models.User;
import com.atifzia.bikerental.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRequestDTO user) {
		userService.registerUser(user);
		return ResponseEntity.ok("User registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user) {
		String token=userService.loginUser(user.getUsername(), user.getPassword());
		return ResponseEntity.ok(token);
	}
}
