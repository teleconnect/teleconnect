package com.teleconnect.service;

import com.teleconnect.dto.UserInfoDTO;
import com.teleconnect.entity.RegistrationUser;
import com.teleconnect.repository.RegistrationUserRepository;
import com.teleconnect.repository.EmailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationUserService {

    @Autowired
    private RegistrationUserRepository userRepository;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void saveUser(RegistrationUser user) {
        userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean emailExistsInVerificationStatus(String email) {
        return emailVerificationRepository.existsByEmail(email);
    }

    public String encryptData(String data) {
        return encoder.encode(data);
    }

    // Method to find user by email
    public RegistrationUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Updated method to retrieve user information without aadharNumber
    public UserInfoDTO findUserInfoByEmail(String email) {
        RegistrationUser user = findByEmail(email);
        if (user != null) {
            return new UserInfoDTO(user.getFirstName(), user.getEmail(), user.getMobileNumber());
        }
        return null;
    }
}
