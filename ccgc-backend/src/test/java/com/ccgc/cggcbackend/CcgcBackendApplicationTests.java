package com.ccgc.cggcbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest

@TestPropertySource(properties = {
		"electricitymap.api.key=ZcY4ZMGEz2kopq9IOC2K" // Prevent failure on startup
})
public class CcgcBackendApplicationTests {
	@Test
	void contextLoads() {
		// Just verifying Spring context loads
	}
}

