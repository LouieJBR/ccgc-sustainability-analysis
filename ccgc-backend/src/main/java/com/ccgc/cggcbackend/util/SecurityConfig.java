package com.ccgc.cggcbackend.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.* ;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity; consider enabling with proper token handling
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/oauth").permitAll() // Allow public access to OAuth endpoint
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/api/auth/success", true) // Redirect after successful login
                        .failureUrl("/api/auth/failure") // Redirect after failed login
                );

        return http.build();
    }
}
