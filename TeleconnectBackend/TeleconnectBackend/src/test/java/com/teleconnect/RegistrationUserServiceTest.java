package com.teleconnect;

import com.teleconnect.entity.RegistrationUser;
import com.teleconnect.repository.EmailVerificationRepository;
import com.teleconnect.repository.RegistrationUserRepository;
import com.teleconnect.service.RegistrationUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistrationUserServiceTest {

    @Mock
    private RegistrationUserRepository userRepository;

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @InjectMocks
    private RegistrationUserService registrationUserService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        // Setup
        RegistrationUser user = new RegistrationUser();
        user.setEmail("test@example.com");

        // Act
        registrationUserService.saveUser(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testEmailExists_True() {
        // Setup
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Act
        boolean result = registrationUserService.emailExists(email);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testEmailExists_False() {
        // Setup
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        // Act
        boolean result = registrationUserService.emailExists(email);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testEmailExistsInVerificationStatus_True() {
        // Setup
        String email = "test@example.com";
        when(emailVerificationRepository.existsByEmail(email)).thenReturn(true);

        // Act
        boolean result = registrationUserService.emailExistsInVerificationStatus(email);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testEmailExistsInVerificationStatus_False() {
        // Setup
        String email = "test@example.com";
        when(emailVerificationRepository.existsByEmail(email)).thenReturn(false);

        // Act
        boolean result = registrationUserService.emailExistsInVerificationStatus(email);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testEncryptData() {
        // Setup
        String data = "myPassword";

        // Act
        String encryptedData = registrationUserService.encryptData(data);

        // Assert
        assertNotEquals(data, encryptedData); // Check that the encrypted data is not the same as the original
        assertTrue(new BCryptPasswordEncoder().matches(data, encryptedData)); // Check that the original data matches the encrypted data
    }

    @Test
    public void testFindByEmail() {
        // Setup
        String email = "test@example.com";
        RegistrationUser user = new RegistrationUser();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Act
        RegistrationUser result = registrationUserService.findByEmail(email);

        // Assert
        assertEquals(user, result);
    }
}
