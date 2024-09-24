package com.teleconnect.dto;

public class UserPlanInfoRequestDTO {
    private String email;

    // Default constructor
    public UserPlanInfoRequestDTO() {}

    // Parameterized constructor
    public UserPlanInfoRequestDTO(String email) {
        this.email = email;
    }

    // Getter and Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
