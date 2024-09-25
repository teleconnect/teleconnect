package com.teleconnect.response;

public class LoginResponse {

    private String status;
    private String message;

    // Default constructor
    public LoginResponse() {
    }

    // Constructor with parameters
    public LoginResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
