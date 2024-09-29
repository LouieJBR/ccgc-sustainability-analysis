package com.ccgc.cggcbackend.controller;

import com.ccgc.cggcbackend.model.User;
import com.ccgc.cggcbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User auth0User) {
        User user = userService.findByAuth0Id(auth0User.getAuth0UserId());
        if (user == null) {
            user = userService.createUser(auth0User);
        }
        return ResponseEntity.ok(user);
    }
}
