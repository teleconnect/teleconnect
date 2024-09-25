package com.teleconnect;

import com.teleconnect.entity.RegistrationUser;
import com.teleconnect.repository.RegistrationUserRepository;
import com.teleconnect.service.LoginService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    @Mock
    private RegistrationUserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    private BCryptPasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        encoder = new BCryptPasswordEncoder(); // Initialize BCryptPasswordEncoder
    }

    @Test
    public void testAuthenticateUser_Success() {
        String email = "test@example.com";
        String rawPassword = "password123";
        String encodedPassword = encoder.encode(rawPassword); // Encode the raw password

        RegistrationUser user = new RegistrationUser();
        user.setEmail(email);
        user.setPassword(encodedPassword); // Store the encoded password

        when(userRepository.findByEmail(email)).thenReturn(user); // Mock the repository response

        boolean result = loginService.authenticateUser(email, rawPassword);
        assertTrue(result, "Expected authentication to succeed");
    }

    @Test
    public void testAuthenticateUser_Failure_UserNotFound() {
        String email = "test@example.com";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(null); // Mock user not found

        boolean result = loginService.authenticateUser(email, password);
        assertFalse(result, "Expected authentication to fail due to user not found");
    }

    @Test
    public void testAuthenticateUser_Failure_InvalidPassword() {
        String email = "test@example.com";
        String rawPassword = "wrongpassword";
        String correctPassword = "password123"; // Correct password
        String encodedPassword = encoder.encode(correctPassword); // Encode the correct password

        RegistrationUser user = new RegistrationUser();
        user.setEmail(email);
        user.setPassword(encodedPassword); // Store the encoded password

        when(userRepository.findByEmail(email)).thenReturn(user); // Mock repository response

        boolean result = loginService.authenticateUser(email, rawPassword);
        assertFalse(result, "Expected authentication to fail due to invalid password");
    }
}
