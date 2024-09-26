package com.teleconnect.service;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class RegistrationOcrService {

    // Method to validate the Aadhaar image without OCR (always returns true)
    public boolean isAadhaarImage(File aadharImage) {
        // Always return true, as we're skipping the OCR validation
        return true;
    }
}
