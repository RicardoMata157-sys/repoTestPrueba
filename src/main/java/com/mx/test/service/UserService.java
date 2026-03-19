package com.mx.test.service;

import com.mx.test.dto.UserRequest;
import com.mx.test.dto.UserResponse;
import com.mx.test.entity.Role;
import com.mx.test.entity.User;
import com.mx.test.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder = null;
    
    public UserService(UserRepository userRepository){
    	this.userRepository  = userRepository;
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse findById(Long id) {
        return toResponse(getUser(id));
    }

    public UserResponse create(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user =new  User();
        user.setFullName(request.getFullName().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword() == null || request.getPassword().isBlank() ? "ChangeMe123" : request.getPassword()));
        user.setRole(Role.USER);
              

        return toResponse(userRepository.save(user));
    }

    public UserResponse update(Long id, UserRequest request) {
        User user = getUser(id);
        user.setFullName(request.getFullName().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return toResponse(userRepository.save(user));
    }

    public void delete(Long id) {
//        userRepository.deleteAll(getUser(id));
    }

    public User getUser(Long id) {
    	User user = userRepository.getById(id);
        return user;
    }

    private UserResponse toResponse(User user) {
    	
    	UserResponse userResponse = new UserResponse();
    	userResponse.setId(user.getId());
    	userResponse.setFullName(user.getFullName());
    	userResponse.setEmail(user.getEmail());
    	userResponse.setRole(user.getRole().name());
    	userResponse.setCreatedAt(user.getCreatedAt());
        return userResponse;
                
    }
}
