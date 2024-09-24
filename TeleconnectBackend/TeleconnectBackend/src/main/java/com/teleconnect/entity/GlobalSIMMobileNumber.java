package com.teleconnect.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "global_mobile_numbers_database")
public class GlobalSIMMobileNumber {

    @Id
    @Column(name = "mobile_number", nullable = false, length = 15)
    private String mobileNumber;

    // Constructor
    public GlobalSIMMobileNumber() {}

    // Getter and Setter
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
