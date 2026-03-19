package com.mx.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.test.dto.AuthResponse;
import com.mx.test.dto.LoginRequest;
import com.mx.test.dto.RegisterRequest;
import com.mx.test.repository.UserRepository;
import com.mx.test.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthService authService; //mejoras al codigo en reduccion de codigo 

//    public AuthController(UserRepository userRepository, AuthService authService) {
//        this.userRepository = userRepository;
//        this.authService =  authService;
//    }

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

		if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body(java.util.Map.of("message", "Email already exists"));
		}

		authService.register(request);

		return ResponseEntity.ok(java.util.Map.of("message", "User registered successfully", "fullName",
				request.getFullName(), "email", request.getEmail()));
	}

	@PostMapping("/login")
	public AuthResponse login(@Valid @RequestBody LoginRequest request) {
		return authService.login(request);
	}
}
