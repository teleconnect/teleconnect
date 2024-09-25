package com.teleconnect.service;

import com.teleconnect.entity.UserPlanAssignment;
import com.teleconnect.entity.ActivateMobileNumber;
import com.teleconnect.entity.GlobalSIMMobileNumber;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import com.teleconnect.repository.ActivateMobileNumberRepository;
import com.teleconnect.repository.GlobalSIMMobileNumberRepository;
import com.teleconnect.repository.RegistrationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserPlanAssignmentService {

    @Autowired
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @Autowired
    private ActivateMobileNumberRepository activateMobileNumberRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RegistrationUserRepository registrationUserRepository;

    @Autowired
    private GlobalSIMMobileNumberRepository globalSIMMobileNumberRepository;

    public UserPlanAssignment assignMobileNumber(String email, String eId, String planId) {
        // Log received inputs
        System.out.println("Received Email: " + email);
        System.out.println("Received EID: " + eId);
        System.out.println("Received Plan ID: " + planId);

        // Validate inputs
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email cannot be null or empty");
        }
        if (eId == null || eId.trim().isEmpty()) {
            throw new RuntimeException("EID cannot be null or empty");
        }
        if (planId == null || planId.trim().isEmpty()) {
            throw new RuntimeException("Plan ID cannot be null or empty");
        }

        // Check if the email is registered
        if (!registrationUserRepository.existsByEmail(email)) {
            throw new RuntimeException("Email not registered");
        }

        // Check for existing entries for the email
        if (!userPlanAssignmentRepository.findAllByEmail(email).isEmpty()) {
            throw new RuntimeException("Mobile Number already assigned");
        }

        // Assign a new mobile number
        Optional<ActivateMobileNumber> availableNumber = activateMobileNumberRepository.findFirstByOrderByMobileIdAsc();
        if (!availableNumber.isPresent()) {
            throw new RuntimeException("No available mobile number found");
        }

        // Create a new assignment
        UserPlanAssignment newAssignment = new UserPlanAssignment();
        newAssignment.setEmail(email);
        newAssignment.setPlanId(planId); // Set the plan ID from the request
        newAssignment.setMobileNumber(availableNumber.get().getMobileNumber());
        newAssignment.setEid(eId); // Set the EID from the request
        newAssignment.setPlanStatus("activated");

        // Set validity_status to 2 minutes from now
        Timestamp validityTimestamp = new Timestamp(System.currentTimeMillis() + 43200 * 60 * 1000);
        newAssignment.setValidityStatus(validityTimestamp);

        // Save the new assignment
        userPlanAssignmentRepository.save(newAssignment);

        // Add the used mobile number to the all_mobile_numbers table
        GlobalSIMMobileNumber globalMobileNumber = new GlobalSIMMobileNumber();
        globalMobileNumber.setMobileNumber(availableNumber.get().getMobileNumber());
        globalSIMMobileNumberRepository.save(globalMobileNumber);

        // Delete the used mobile number from the available numbers
        activateMobileNumberRepository.deleteById(availableNumber.get().getMobileId());

        // Send the activation email
        sendActivationEmail(email, newAssignment.getMobileNumber(), planId);

        return newAssignment;
    }

    private void sendActivationEmail(String email, String mobileNumber, String planId) {
        String planType;
        switch (planId) {
            case "plan-basic":
                planType = "PLAN ACTIVATED: BASIC PLAN";
                break;
            case "plan-standard":
                planType = "PLAN ACTIVATED: STANDARD PLAN";
                break;
            case "plan-premium":
                planType = "PLAN ACTIVATED: PREMIUM PLAN";
                break;
            default:
                planType = "NOT VALID PLAN";
                break;
        }

        // Create the email content
        String emailContent = String.format("Activated with Telstra!\nYour mobile number with Telstra is: %s\n%s", mobileNumber, planType);

        // Prepare and send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Telstra Mobile Number Activation");
        message.setText(emailContent);

        try {
            // Send the email
            mailSender.send(message);
        } catch (MailException e) {
            // Handle mail sending error
            System.err.println("Unable to Send the Mail: " + e.getMessage());
            throw new RuntimeException("Unable to Send the Mail");
        }
    }
}
