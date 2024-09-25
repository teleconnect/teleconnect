package com.teleconnect.dto;

public class UserInfoDTO {
    private String firstName;
    private String email;
    private String mobileNumber;

    // Constructor
    public UserInfoDTO(String firstName, String email, String mobileNumber) {
        this.firstName = firstName;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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
