package com.ccgc.cggcbackend.repository;


import com.ccgc.cggcbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by their email address
    Optional<User> findByEmail(String email);

    // Find a user by their provider ID (e.g., Google ID, GitHub ID)
    Optional<User> findByProviderId(String providerId);
}

