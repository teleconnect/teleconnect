package com.teleconnect.service;

import com.teleconnect.entity.AdminLogin;
import com.teleconnect.entity.UserPlanAssignment;
import com.teleconnect.repository.AdminLoginRepository;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class AdminLoginService {

    @Autowired
    private AdminLoginRepository adminLoginRepository;

    @Autowired
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    private static final String KEY = "TELECONNECTBYPUNETELSTRA"; // AES Key

    public String login(String email, String password) {
        AdminLogin admin = adminLoginRepository.findByEmail(email);
        
        if (admin != null && decrypt(admin.getPassword()).equals(password)) {
            return "Login successful!";
        }
        
        throw new RuntimeException("Invalid email or password.");
    }

    public void deleteUserByEmail(String email) {
        List<UserPlanAssignment> assignments = userPlanAssignmentRepository.findAllByEmail(email);
        if (!assignments.isEmpty()) {
            userPlanAssignmentRepository.deleteAll(assignments);
        } else {
            throw new RuntimeException("No user found with the given email.");
        }
    }

    private String decrypt(String encryptedPassword) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = new byte[16]; // Use the same IV used during encryption
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
