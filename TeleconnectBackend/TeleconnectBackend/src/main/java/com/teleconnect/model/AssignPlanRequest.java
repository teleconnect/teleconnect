package com.teleconnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignPlanRequest {
    private String email;
    private String planId;

    @JsonProperty("eId") // This will map the JSON field "eId" to this variable
    private String eid;

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

    public String getEid() { // Change from getEId to getEid to match the field name
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }
}
