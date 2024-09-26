package com.teleconnect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.teleconnect.service.RegistrationOcrService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationOcrServiceTest {

    private RegistrationOcrService registrationOcrService;

    @BeforeEach
    public void setUp() {
        registrationOcrService = new RegistrationOcrService();
    }

    @Test
    public void testIsAadhaarImage_AlwaysValid() {
        // Since we no longer check the content and always return true for Aadhaar validation,
        // this test ensures that isAadhaarImage always returns true
        boolean result = registrationOcrService.isAadhaarImage(null);  // Input doesn't matter now
        assertTrue(result);
    }
}
