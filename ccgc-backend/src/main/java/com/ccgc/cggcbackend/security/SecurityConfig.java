package com.ccgc.cggcbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // ✅ Public
                        .anyRequest().authenticated()                // 🔐 Others protected
                )
                .httpBasic(Customizer.withDefaults())                 // Optional: or use JWT later
                .csrf(csrf -> csrf.disable())                         // ✅ Disable CSRF
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwkSetUri("https://dev-m1vbm7mjkcjugfu4.uk.auth0.com/.well-known/jwks.json")
                        )
                )
                .build();
    }
}

