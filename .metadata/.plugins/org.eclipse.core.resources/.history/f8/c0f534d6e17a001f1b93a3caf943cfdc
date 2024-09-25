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

        for (UserPlanAssignment assignment : assignments) {
            if ("activated".equalsIgnoreCase(assignment.getPlanStatus())) {
                hasActivated = true;
                Timestamp currentValidity = assignment.getValidityStatus();

                if (currentValidity != null) {
                    Timestamp newValidity = Timestamp.from(Instant.ofEpochMilli(currentValidity.getTime() + 120_000));
                    assignment.setValidityStatus(newValidity);
                    assignment.setPlanId(planId); // Update planId

                    try {
                        userPlanAssignmentRepository.save(assignment);
                        String emailResponse = sendRechargeSuccessEmail(email, assignment.getMobileNumber(), newValidity);
                        if (!emailResponse.equals("Email sent successfully")) {
                            return emailResponse;
                        }
                        return "Recharge Successful !! Plan ID updated.";
                    } catch (DataIntegrityViolationException ex) {
                        return "Conflict occurred while updating the plan. Please try again.";
                    }
                } else {
                    return "Unable to process the recharge at this moment !!";
                }
            }
        }

        // If no activated entries are found, add a new entry
        if (!hasActivated) {
            UserPlanAssignment existingAssignment = assignments.get(0);
            UserPlanAssignment newAssignment = new UserPlanAssignment();
            newAssignment.setPlanId(planId); // Set new planId
            newAssignment.setEid(existingAssignment.getEid());
            newAssignment.setMobileNumber(existingAssignment.getMobileNumber());
            newAssignment.setEmail(email);
            newAssignment.setPlanStatus("activated");
            newAssignment.setValidityStatus(Timestamp.from(Instant.now().plusSeconds(120)));

            try {
                userPlanAssignmentRepository.save(newAssignment);
                String emailResponse = sendRechargeSuccessEmail(email, newAssignment.getMobileNumber(), newAssignment.getValidityStatus());
                if (!emailResponse.equals("Email sent successfully")) {
                    return emailResponse;
                }
                return "Mobile Number activated again !! Recharge Successful !";
            } catch (DataIntegrityViolationException ex) {
                return "Conflict occurred while creating a new plan. Please try again.";
            }
        }

        return "Unable to process the recharge at this moment !!";
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
