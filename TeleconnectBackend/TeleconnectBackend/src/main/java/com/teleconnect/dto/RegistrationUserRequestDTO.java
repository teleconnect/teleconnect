package com.teleconnect.dto;

import org.springframework.web.multipart.MultipartFile;

public class RegistrationUserRequestDTO {
    private String firstName;
    private String email;
    private String aadharNumber;
    private String password;
    private String mobileNumber;
    private MultipartFile aadharImage;

    // Default constructor
    public RegistrationUserRequestDTO() {}

    // Parameterized constructor
    public RegistrationUserRequestDTO(String firstName, String email, String aadharNumber, String password, String mobileNumber, MultipartFile aadharImage) {
        this.firstName = firstName;
        this.email = email;
        this.aadharNumber = aadharNumber;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.aadharImage = aadharImage;
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

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public MultipartFile getAadharImage() {
        return aadharImage;
    }

    public void setAadharImage(MultipartFile aadharImage) {
        this.aadharImage = aadharImage;
    }
}
