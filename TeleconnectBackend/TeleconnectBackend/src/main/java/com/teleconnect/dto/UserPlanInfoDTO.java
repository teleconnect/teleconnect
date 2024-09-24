package com.teleconnect.dto;

import java.sql.Timestamp;

public class UserPlanInfoDTO {
    private String planId;
    private Timestamp validityStatus;
    private Timestamp createdAt;

    public UserPlanInfoDTO(String planId, Timestamp validityStatus, Timestamp createdAt) {
        this.planId = planId;
        this.validityStatus = validityStatus;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Timestamp getValidityStatus() {
        return validityStatus;
    }

    public void setValidityStatus(Timestamp validityStatus) {
        this.validityStatus = validityStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
