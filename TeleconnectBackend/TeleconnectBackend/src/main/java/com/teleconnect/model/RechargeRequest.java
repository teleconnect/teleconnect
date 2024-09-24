package com.teleconnect.model;

public class RechargeRequest {
    private String email;
    private String planId;

    // Default constructor
    public RechargeRequest() {}

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
