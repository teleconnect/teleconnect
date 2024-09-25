package com.teleconnect.dto;

public class AdminLoginRequestDTO {
    private String email;
    private String password;

    // Default constructor
    public AdminLoginRequestDTO() {}

    // Parameterized constructor
    public AdminLoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
