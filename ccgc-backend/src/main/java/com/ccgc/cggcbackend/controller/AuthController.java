package com.ccgc.cggcbackend.controller;

import com.ccgc.cggcbackend.model.User;
import com.ccgc.cggcbackend.repository.UserRepository;
import com.ccgc.cggcbackend.request.OAuthTokenRequest;
import com.ccgc.cggcbackend.service.OAuth2TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final OAuth2TokenService oauth2TokenService;
    private final UserRepository userRepository;

    public AuthController(OAuth2TokenService oauth2TokenService, UserRepository userRepository) {
        this.oauth2TokenService = oauth2TokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/oauth")
    public String authenticateUser(@RequestBody OAuthTokenRequest tokenRequest) {
        // Validate the token and get user information
        User user = oauth2TokenService.getUserInfoFromToken(tokenRequest.getToken());

        System.out.println("USER-FROM-TOKEN:" + user);

        // Check if the user already exists
        if (user == null) {
            // Create a new user entry if it's the first login
            user = new User();
            user.setEmail(tokenRequest.getEmail());
            user.setName(tokenRequest.getName());
            user.setProvider(tokenRequest.getProvider());
            user.setProviderId(tokenRequest.getProviderId());
            System.out.println("NEW-USER-CREATION:" + user);

            userRepository.save(user);
        }

        // Return a success message or JWT token
        return "User authenticated and data saved successfully!";
    }
}
