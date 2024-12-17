import org.springframework.beans.factory.annotation.Bean;
import org.springframework.beans.factory.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

//ToDo: UPDATE THIS ONCE CONNECTION TO SQL FINALISED

@Configuration
@EnableWebSecurity
public class SecurityConfig {

}