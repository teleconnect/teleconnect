package com.teleconnect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.teleconnect.service.RegistrationOcrService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RegistrationOcrServiceTest {

    private RegistrationOcrService registrationOcrService;

    @BeforeEach
    public void setUp() {
        registrationOcrService = new RegistrationOcrService();
    }

    @Test
    public void testExtractAadhaarNumber_ValidInput() {
        String ocrOutput = "Aadhar number is 1234 5678 9012";
        String result = registrationOcrService.extractAadharNumber(ocrOutput);
        assertEquals("123456789012", result);
    }

    @Test
    public void testExtractAadhaarNumber_InvalidInput() {
        String result = registrationOcrService.extractAadharNumber("random text");
        assertNull(result);
    }

    @Test
    public void testExtractAadhaarNumber_EmptyInput() {
        String result = registrationOcrService.extractAadharNumber("");
        assertNull(result);
    }

    @Test
    public void testIsAadhaarImage_ValidAadhaar() {
        String ocrOutput = "My Aadhar number is 1234 5678 9012";
        boolean result = registrationOcrService.extractAadharNumber(ocrOutput) != null;
        assertEquals(true, result);
    }

    @Test
    public void testIsAadhaarImage_InvalidAadhaar() {
        boolean result = registrationOcrService.extractAadharNumber("This is not a valid Aadhar") != null;
        assertEquals(false, result);
    }
}
