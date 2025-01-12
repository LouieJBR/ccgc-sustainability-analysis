package com.ccgc.cggcbackend.service;

import com.ccgc.cggcbackend.model.User;
import com.ccgc.cggcbackend.repository.UserRepository;
import com.ccgc.cggcbackend.request.OAuthTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuth2TokenService {

    @Autowired
    private UserRepository userRepository;

    private static final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo"; // Google API for user info

    // Method to get user info from OAuth token
    public User getUserInfoFromToken(String token) {
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Construct the URL for fetching user information (Google API URL with the token)
        String url = GOOGLE_USER_INFO_URL + "?access_token=" + token;

        // Get the response from Google (error handling and JSON parsing are assumed)
        String response = restTemplate.getForObject(url, String.class);

        // Extract user details from the response (in a real scenario, you'd parse JSON)
        // You can use Jackson or another JSON parsing library here (for now, mock values)
        String email = extractEmailFromResponse(response);  // Extract from response JSON
        String name = extractNameFromResponse(response);    // Extract from response JSON
        String providerId = extractProviderIdFromResponse(response); // Extract from response JSON

        // Check if the user already exists based on email or providerId
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.findByProviderId(providerId).orElse(null));

        if (user == null) {
            // If the user doesn't exist, create a new user entry
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setProvider("Google"); // Hardcoded for now, can be dynamic based on OAuth provider
            user.setProviderId(providerId);
            userRepository.save(user);
        }

        return user;
    }

    // Mock response parsing methods (in a real implementation, use JSON parsing library)
    private String extractEmailFromResponse(String response) {
        // Assuming the response contains the email field (parse properly with a JSON library)
        return "user@example.com"; // Hardcoded, replace with actual extraction logic
    }

    private String extractNameFromResponse(String response) {
        // Assuming the response contains the name field (parse properly with a JSON library)
        return "John Doe"; // Hardcoded, replace with actual extraction logic
    }

    private String extractProviderIdFromResponse(String response) {
        // Assuming the response contains the providerId field (parse properly with a JSON library)
        return "google-user-id"; // Hardcoded, replace with actual extraction logic
    }
}
