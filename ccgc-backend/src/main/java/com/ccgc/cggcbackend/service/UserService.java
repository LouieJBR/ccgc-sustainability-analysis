package com.ccgc.cggcbackend.service;

import com.ccgc.cggcbackend.model.User;
import com.ccgc.cggcbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByAuth0Id(String auth0Id) {
        return userRepository.findByAuth0UserId(auth0Id);
    }

    public User createUser(User auth0User) {
        User user = new User();
        user.setAuth0UserId(auth0User.getAuth0UserId());
        user.setEmail(auth0User.getEmail());
        return userRepository.save(user);
    }

}
