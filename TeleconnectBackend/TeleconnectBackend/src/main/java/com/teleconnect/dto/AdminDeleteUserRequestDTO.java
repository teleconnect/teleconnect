package com.teleconnect.dto;

public class AdminDeleteUserRequestDTO {
    private String email;

    // Default constructor
    public AdminDeleteUserRequestDTO() {}

    // Parameterized constructor
    public AdminDeleteUserRequestDTO(String email) {
        this.email = email;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
