package com.ccgc.cggcbackend.service;

import com.ccgc.cggcbackend.model.User;
import com.ccgc.cggcbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired  // Optional with single constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(String username, String email) {
        User user = new User(username, email);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
