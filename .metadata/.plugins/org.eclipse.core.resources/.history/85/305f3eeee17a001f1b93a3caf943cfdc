package com.teleconnect.controller;

import com.teleconnect.model.AdminLoginRequestDTO;
import com.teleconnect.model.AdminDeleteUserRequestDTO;
import com.teleconnect.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AdminLoginRequestDTO loginRequest) {
        try {
            String result = adminLoginService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestBody AdminDeleteUserRequestDTO deleteUserRequest) {
        try {
            adminLoginService.deleteUserByEmail(deleteUserRequest.getEmail());
            return ResponseEntity.ok("User deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
