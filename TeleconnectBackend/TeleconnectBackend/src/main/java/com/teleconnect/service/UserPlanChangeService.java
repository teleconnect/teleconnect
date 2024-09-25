package com.teleconnect.service;

import com.teleconnect.entity.UserPlanAssignment;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage; // Import for sending email
import org.springframework.mail.javamail.JavaMailSender; // Import for JavaMailSender
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserPlanChangeService {

    @Autowired
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @Autowired
    private JavaMailSender mailSender; // Mail sender bean

    public String changePlan(String email, String planId) {
        List<UserPlanAssignment> assignments = userPlanAssignmentRepository.findAllByEmail(email);

        // Check if the user is onboarded
        if (assignments.isEmpty()) {
            return "User not Onboarded"; // Return error message instead of throwing exception
        }

        // Check if all entries are deactivated
        boolean allDeactivated = assignments.stream().allMatch(a -> "deactivated".equalsIgnoreCase(a.getPlanStatus()));
        if (allDeactivated) {
            return "Mobile Number Deactivated"; // Return error message instead of throwing exception
        }

        // Find the first activated plan and change it
        for (UserPlanAssignment assignment : assignments) {
            if ("activated".equalsIgnoreCase(assignment.getPlanStatus())) {

                // Check if the current activated planId is the same as the provided planId
                if (assignment.getPlanId().equals(planId)) {
                    return "Same Plan already activated for user"; // Return error message
                }

                // Check if the time difference is more than one minute
                long currentTime = System.currentTimeMillis();
                long createdAtTime = assignment.getCreatedAt().getTime();
                long timeDifference = currentTime - createdAtTime;

                if (timeDifference <= TimeUnit.MINUTES.toMillis(1440)) {
                    return "Not Eligible to change the Plan"; // Return error message if within one minute
                }

                // Change the existing plan to deactivated
                assignment.setPlanStatus("deactivated");
                assignment.setUpdatedAt(new Timestamp(currentTime));
                userPlanAssignmentRepository.save(assignment); // Update the existing entry

                // Create a new assignment with the new plan
                UserPlanAssignment newAssignment = new UserPlanAssignment();
                newAssignment.setEmail(email);
                newAssignment.setPlanId(planId);
                newAssignment.setMobileNumber(assignment.getMobileNumber()); // Keep the same mobile number
                newAssignment.setEid(assignment.getEid()); // Keep the same EID
                newAssignment.setPlanStatus("activated");
                newAssignment.setCreatedAt(new Timestamp(currentTime)); // Set created timestamp

                Timestamp validityStatus = new Timestamp(currentTime + TimeUnit.MINUTES.toMillis(43200));
                newAssignment.setValidityStatus(validityStatus);

                userPlanAssignmentRepository.save(newAssignment); // Save the new entry

                // Send email notification about the plan change
                sendPlanChangeEmail(email, planId);

                return "Plan changed successfully";
            }
        }

        return "No active plan found to change"; // Return message if no active plan found
    }

    private void sendPlanChangeEmail(String email, String planId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Plan Change Notification");
        message.setText("Your plan has been changed to: " + planId);
        mailSender.send(message); // Send the email
    }
}
