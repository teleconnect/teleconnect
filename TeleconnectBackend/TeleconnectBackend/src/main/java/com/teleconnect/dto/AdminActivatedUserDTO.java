package com.teleconnect.dto;

public class AdminActivatedUserDTO {
    private String email;
    private String mobileNumber;

    public AdminActivatedUserDTO(String email, String mobileNumber) {
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}