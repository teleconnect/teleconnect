package com.teleconnect.controller;

import com.teleconnect.model.LoginRequest;
import com.teleconnect.model.LoginResponse;
import com.teleconnect.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/login")
public class UserLoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        boolean isSuccess = loginService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (isSuccess) {
            return ResponseEntity.ok(new LoginResponse("Success", "Login successful."));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("Error", "Invalid email or password."));
        }
    }
}
