package com.ccgc.cggcbackend.controller;

import com.ccgc.cggcbackend.model.User;
import com.ccgc.cggcbackend.repository.UserRepository;
import com.ccgc.cggcbackend.request.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/oauth")
    public ResponseEntity<?> handleOAuth(@RequestBody AuthRequest request) {
        Optional<User> existingUser = userRepository.findByAuth0Id(request.getAuth0Id());

        if (existingUser.isPresent()) {
            return ResponseEntity.ok(existingUser.get());
        }

        // Create a new user if not exists
        User newUser = new User();
        newUser.setAuth0Id(request.getAuth0Id());
        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());

        userRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }
}

