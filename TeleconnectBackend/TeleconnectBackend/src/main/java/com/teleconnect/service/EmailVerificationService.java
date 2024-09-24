package com.teleconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.teleconnect.entity.EmailVerification;
import com.teleconnect.repository.EmailVerificationRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailVerificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    // Regex pattern for basic email validation ending with @gmail.com
    private static final String GMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@gmail\\.com$";

    // Generates a 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // Sends a success email with OTP
    private boolean sendVerificationEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Telstra Email Verification !!");
        message.setText("The OTP for verification is: " + otp);

        try {
            mailSender.send(message);
            System.out.println("OTP sent to user: " + otp);
            return true; // Indicate success
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Indicate failure
        }
    }

    // Validates the email format and domain
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(GMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Verify email and send OTP only if the email is not already verified and is a Gmail address
    public String verifyEmail(String email, HttpSession session) {
        System.out.println("Session ID in verifyEmail: " + session.getId());
        
        // Check if the email has a valid format and ends with @gmail.com
        if (!isValidEmail(email)) {
            return "Invalid email format! Please use a Gmail address.";
        }
        
        // Check if the email already exists in the email_verification table
        boolean emailExists = emailVerificationRepository.existsByEmail(email);
        if (emailExists) {
            return "Email is already verified!";
        }

        // Send verification email with OTP
        String otp = generateOtp();
        boolean emailSent = sendVerificationEmail(email, otp);
        
        if (!emailSent) {
            return "Failed to send OTP. Please try again.";
        }

        // Store OTP and email in session for later validation
        session.setAttribute("otp", otp);
        session.setAttribute("email", email);

        return "OTP sent to " + email;
    }

    // Validate OTP
    @Transactional
    public String validateOtp(String otp, HttpSession session) {
        System.out.println("Session ID in validateOtp: " + session.getId());
        String storedOtp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("email");
        System.out.println("Stored OTP: " + storedOtp + ", Email: " + email);

        // Validate OTP
        if (storedOtp != null && storedOtp.equals(otp) && email != null) {
            // Remove OTP and email from session
            session.removeAttribute("otp");
            session.removeAttribute("email");

            // Save the email in the email_verification table
            EmailVerification emailVerification = new EmailVerification();
            emailVerification.setEmail(email);
            emailVerificationRepository.save(emailVerification);

            return "OTP validation successful!";
        } else if (email == null) {
            return "Error: Email not found in session.";
        } else {
            return "Invalid OTP. Please try again.";
        }
    }
}
