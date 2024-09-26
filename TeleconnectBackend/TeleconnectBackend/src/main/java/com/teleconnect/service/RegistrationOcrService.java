package com.teleconnect.service;

import org.springframework.stereotype.Service;

@Service
public class RegistrationOcrService {

    // Constructor
    public RegistrationOcrService() {
        // No initialization needed for Tesseract
    }

    // Method to extract Aadhaar number (modified to just return true)
    public boolean extractAadhaarNumberFromImage() {
        return true;
    }

    // Method to check if an image is an Aadhaar image (modified to just return true)
    public boolean isAadhaarImage() {
        return true;
    }

    // Method to extract Aadhaar number (modified to just return true)
    public boolean extractAadharNumber() {
        return true;
    }
}
