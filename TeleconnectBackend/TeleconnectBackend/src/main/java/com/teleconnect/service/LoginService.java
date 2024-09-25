package com.teleconnect.service;

import com.teleconnect.entity.RegistrationUser;
import com.teleconnect.repository.RegistrationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private RegistrationUserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean authenticateUser(String email, String password) {
        RegistrationUser user = userRepository.findByEmail(email);
        if (user != null) {
            return encoder.matches(password, user.getPassword());
        }
        return false;
    }
}
