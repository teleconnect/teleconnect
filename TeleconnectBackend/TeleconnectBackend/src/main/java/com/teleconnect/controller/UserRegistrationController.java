package com.teleconnect.controller;

import com.teleconnect.dto.RegistrationUserRequestDTO;
import com.teleconnect.dto.UserInfoDTO;
import com.teleconnect.entity.RegistrationUser;
import com.teleconnect.response.RegistrationUserResponse;
import com.teleconnect.service.RegistrationOcrService;
import com.teleconnect.service.RegistrationUserService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;package com.teleconnect.controller;

import com.teleconnect.dto.RegistrationUserRequestDTO;
import com.teleconnect.dto.UserInfoDTO;
import com.teleconnect.entity.RegistrationUser;
import com.teleconnect.response.RegistrationUserResponse;
import com.teleconnect.service.RegistrationOcrService;
import com.teleconnect.service.RegistrationUserService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://44.201.255.255")
@RequestMapping("/api/user")
public class UserRegistrationController {

    @Autowired
    private RegistrationUserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationUserResponse> registerUser(
            @ModelAttribute RegistrationUserRequestDTO registrationUserRequestDTO) {

        String firstName = registrationUserRequestDTO.getFirstName();
        String email = registrationUserRequestDTO.getEmail();
        String aadharNumber = registrationUserRequestDTO.getAadharNumber();
        String password = registrationUserRequestDTO.getPassword();
        String mobileNumber = registrationUserRequestDTO.getMobileNumber();
        MultipartFile aadharImage = registrationUserRequestDTO.getAadharImage();

        // Check if the Aadhar image is provided
        if (aadharImage.isEmpty()) {
            return ResponseEntity.badRequest().body(new RegistrationUserResponse("Error", "Aadhar card image is required."));
        }

        // Check if the email is already registered
        if (userService.emailExists(email)) {
            return ResponseEntity.badRequest().body(new RegistrationUserResponse("Error", "Email is already registered."));
        }

        try {
            // Here we assume the Aadhar image is always valid, skipping Tesseract OCR validation
            boolean isAadharImageValid = true; // Always set to true

            if (!isAadharImageValid) {
                return ResponseEntity.badRequest().body(new RegistrationUserResponse("Error", "Invalid Aadhar card image."));
            }

            // Check if the email exists in the verification status table
            if (!userService.emailExistsInVerificationStatus(email)) {
                return ResponseEntity.badRequest().body(new RegistrationUserResponse("Error", "Email is not present in verification status table."));
            }

            // Encrypt Aadhar number and password before saving
            String encryptedAadharNumber = userService.encryptData(aadharNumber);
            String encryptedPassword = userService.encryptData(password);

            // Create a new RegistrationUser object and save it
            RegistrationUser user = new RegistrationUser(firstName, email, encryptedAadharNumber, mobileNumber, encryptedPassword);
            userService.saveUser(user);

            return ResponseEntity.ok(new RegistrationUserResponse("Success", "Registration Successful"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RegistrationUserResponse("Error", "Failed to process the request."));
        }
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDTO> getUserInfoByEmail(@RequestParam String email) {
        UserInfoDTO userInfo = userService.findUserInfoByEmail(email);
        if (userInfo != null) {
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RegistrationUserResponse> handleException(Exception ex) {
        return ResponseEntity.status(500).body(new RegistrationUserResponse("Error", "An unexpected error occurred."));
    }
}

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;@CrossOrigin(origins = "http://44.201.255.255")

@RestController
@CrossOrigin(origins = "http://44.201.255.255")
@RequestMapping("/api/user")
public class UserRegistrationController {

    @Autowired
    private RegistrationUserService userService;

    @Autowired
    private RegistrationOcrService ocrService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationUserResponse> registerUser(
            @ModelAttribute RegistrationUserRequestDTO registrationUserRequestDTO) {

        String firstName = registrationUserRequestDTO.getFirstName();
        String email = registrationUserRequestDTO.getEmail();
        String aadharNumber = registrationUserRequestDTO.getAadharNumber();
        String password = registrationUserRequestDTO.getPassword();
        String mobileNumber = registrationUserRequestDTO.getMobileNumber();
        MultipartFile aadharImage = registrationUserRequestDTO.getAadharImage();

        // Check if the Aadhar image is provided
        if (aadharImage.isEmpty()) {
            return ResponseEntity.badRequest().body(new RegistrationUserResponse("Error", "Aadhar card image is required."));
        }

        // Check if the email is already registered
        if (userService.emailExists(email)) {
            return ResponseEntity.badRequest().body(new RegistrationUserResponse("Error", "Email is already registered."));
        }

        File tempFile;
        try {
            // Create a temporary file to store the uploaded image
            tempFile = File.createTempFile("aadhar-", aadharImage.getOriginalFilename());
            aadharImage.transferTo(tempFile);

            // Check if the uploaded image is a valid Aadhar card image
            if (!ocrService.isAadhaarImage(tempFile)) {
                tempFile.delete();
                return ResponseEntity.badRequest().body(new RegistrationUserResponse("Error", "Invalid Aadhar card image."));
            }

            // Extract the Aadhar number from the image using OCR
            String extractedAadharNumber = ocrService.extractAadhaarNumberFromImage(tempFile);
            if (!extractedAadharNumber.equals(aadharNumber)) {
                tempFile.delete();
                return ResponseEntity.badRequest().body(new RegistrationUserResponse("Error", "Aadhar number does not match."));
            }

            // Check if the email exists in the verification status table
            if (!userService.emailExistsInVerificationStatus(email)) {
                tempFile.delete();
                return ResponseEntity.badRequest().body(new RegistrationUserResponse("Error", "Email is not present in verification status table."));
            }

            // Encrypt Aadhar number and password before saving
            String encryptedAadharNumber = userService.encryptData(aadharNumber);
            String encryptedPassword = userService.encryptData(password);

            // Create a new RegistrationUser object and save it
            RegistrationUser user = new RegistrationUser(firstName, email, encryptedAadharNumber, mobileNumber, encryptedPassword);
            userService.saveUser(user);

            // Delete the temporary file after processing
            tempFile.delete();

            return ResponseEntity.ok(new RegistrationUserResponse("Success", "Registration Successful"));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new RegistrationUserResponse("Error", "Failed to process the image."));
        } catch (TesseractException e) {
            return ResponseEntity.status(500).body(new RegistrationUserResponse("Error", "Failed to extract Aadhar number from the image."));
        }
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDTO> getUserInfoByEmail(@RequestParam String email) {
        UserInfoDTO userInfo = userService.findUserInfoByEmail(email);
        if (userInfo != null) {
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RegistrationUserResponse> handleException(Exception ex) {
        return ResponseEntity.status(500).body(new RegistrationUserResponse("Error", "An unexpected error occurred."));
    }
}
