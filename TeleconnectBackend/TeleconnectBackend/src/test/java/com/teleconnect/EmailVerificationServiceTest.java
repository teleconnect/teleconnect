package com.teleconnect;

import com.teleconnect.repository.EmailVerificationRepository;
import com.teleconnect.service.EmailVerificationService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmailVerificationServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @Mock
    private HttpSession session;

    @InjectMocks
    private EmailVerificationService emailVerificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVerifyEmail_WithValidEmail_SendsOtp() {
        String email = "test@gmail.com";
        when(emailVerificationRepository.existsByEmail(email)).thenReturn(false);

        String result = emailVerificationService.verifyEmail(email, session);

        assertTrue(result.contains("OTP sent to"));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class)); // Specify SimpleMailMessage explicitly
    }

    @Test
    void testValidateOtp_WithValidOtp_SuccessfulValidation() {
        String otp = "123456";
        String email = "test@gmail.com";
        when(session.getAttribute("otp")).thenReturn(otp);
        when(session.getAttribute("email")).thenReturn(email);

        String result = emailVerificationService.validateOtp(otp, session);

        assertEquals("OTP validation successful!", result);
        verify(emailVerificationRepository, times(1)).save(any());
    }
}
