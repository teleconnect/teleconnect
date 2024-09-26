package com.teleconnect.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class RegistrationOcrService {

    // Constructor
    public RegistrationOcrService() {
        // No initialization needed for Tesseract
    }

    // Method to extract Aadhaar number from an image
    public boolean extractAadhaarNumberFromImage(File aadharImage) {
        // Return true without performing any logic
        return true;
    }

    // Method to check if an image is an Aadhaar image
    public boolean isAadhaarImage(File aadharImage) {
        // Return true without performing any logic
        return true;
    }

    // Method to extract Aadhaar number from OCR text
    public boolean extractAadharNumber(String ocrText) {
        // Return true without performing any logic
        return true;
    }
}
