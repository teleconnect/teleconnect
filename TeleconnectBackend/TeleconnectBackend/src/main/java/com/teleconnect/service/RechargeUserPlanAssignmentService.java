package com.teleconnect.service;

import com.teleconnect.entity.UserPlanAssignment;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RechargeUserPlanAssignmentService {

    @Autowired
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = Logger.getLogger(RechargeUserPlanAssignmentService.class.getName());

    public String updateValidityStatus(String email, String planId) {
        List<UserPlanAssignment> assignments = userPlanAssignmentRepository.findAllByEmail(email);

        if (assignments.isEmpty()) {
            return "User with the given email not found.";
        }

        boolean hasActivated = false;

        // Check if there is an activated entry
        for (UserPlanAssignment assignment : assignments) {
            if ("activated".equalsIgnoreCase(assignment.getPlanStatus())) {
                hasActivated = true;
                // Deactivate the current activated entry and update the updatedAt timestamp
                assignment.setPlanStatus("deactivated");
                assignment.setUpdatedAt(Timestamp.from(Instant.now())); // Update the updatedAt field
                try {
                    userPlanAssignmentRepository.save(assignment);
                } catch (DataIntegrityViolationException ex) {
                    return "Conflict occurred while deactivating the current plan. Please try again.";
                }
                break;
            }
        }

        // If no activated entries are found, get the latest entry
        if (!hasActivated) {
            UserPlanAssignment latestAssignment = assignments.get(assignments.size() - 1);
            latestAssignment.setPlanStatus("deactivated");
            latestAssignment.setUpdatedAt(Timestamp.from(Instant.now())); // Update the updatedAt field
            try {
                userPlanAssignmentRepository.save(latestAssignment);
            } catch (DataIntegrityViolationException ex) {
                return "Conflict occurred while deactivating the current plan. Please try again.";
            }
        }

        // Add a new activated entry
        UserPlanAssignment existingAssignment = assignments.get(0);
        UserPlanAssignment newAssignment = new UserPlanAssignment();
        newAssignment.setPlanId(planId); // Set new planId
        newAssignment.setEid(existingAssignment.getEid());
        newAssignment.setMobileNumber(existingAssignment.getMobileNumber());
        newAssignment.setEmail(email);
        newAssignment.setPlanStatus("activated");
        newAssignment.setValidityStatus(Timestamp.from(Instant.now().plusSeconds(2592000)));
        newAssignment.setUpdatedAt(Timestamp.from(Instant.now())); // Set the updatedAt field for the new entry

        try {
            userPlanAssignmentRepository.save(newAssignment);
            String emailResponse = sendRechargeSuccessEmail(email, newAssignment.getMobileNumber(), newAssignment.getValidityStatus());
            if (!emailResponse.equals("Email sent successfully")) {
                return emailResponse;
            }
            return "Recharge Successful !! New Plan Activated.";
        } catch (DataIntegrityViolationException ex) {
            return "Conflict occurred while creating a new plan. Please try again.";
        }
    }

    private String sendRechargeSuccessEmail(String toEmail, String mobileNumber, Timestamp validityStatus) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Recharge Successful");
        message.setText("Dear User,\n\nYour recharge has been successful.\n" +
                "Your mobile number " + mobileNumber + " is now valid till " + validityStatus.toString() + ".\n\n" +
                "Thank you for using our service!\n\n" +
                "Best Regards,\nTeleconnect Team");

        try {
            mailSender.send(message);
            return "Email sent successfully";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to send email", e);
            return "Unable to send recharge success email.";
        }
    }
}