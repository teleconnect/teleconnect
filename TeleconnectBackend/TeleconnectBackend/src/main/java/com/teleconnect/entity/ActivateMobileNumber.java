package com.teleconnect.entity;


@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "teleconnect_e_sim_pool")
public class ActivateMobileNumber {

    @jakarta.persistence.Id
    private Long mobileId;

    private String mobileNumber;

    // Constructors

    public ActivateMobileNumber() {
    }

    public ActivateMobileNumber(Long mobileId, String mobileNumber) {
        this.mobileId = mobileId;
        this.mobileNumber = mobileNumber;
    }

    // Getters and Setters

    public Long getMobileId() {
        return mobileId;
    }

    public void setMobileId(Long mobileId) {
        this.mobileId = mobileId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "ActivateMobileNumber{" +
                "mobileId=" + mobileId +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
