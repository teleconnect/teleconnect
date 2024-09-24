package com.teleconnect.scheduler;

import com.teleconnect.entity.UserPlanAssignment;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class UserPlanAssignmentScheduler {

    @Autowired
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = Logger.getLogger(UserPlanAssignmentScheduler.class.getName());

    @Scheduled(fixedRate = 60000) // Runs every minute (60000 milliseconds)
    public void updatePlanStatus() {
        List<UserPlanAssignment> activatedPlans = userPlanAssignmentRepository.findAllByPlanStatus("activated");

        for (UserPlanAssignment assignment : activatedPlans) {
            Timestamp validityStatus = assignment.getValidityStatus();

            // Check if the current time-stamp is greater than validity_status
            if (validityStatus != null && Timestamp.from(Instant.now()).after(validityStatus)) {
                String mobileNumber = assignment.getMobileNumber(); // Get the mobile number
                assignment.setPlanStatus("deactivated");
                
                // Send email notification with mobile number
                sendDeactivationEmail(assignment.getEmail(), mobileNumber);

                userPlanAssignmentRepository.save(assignment);
            }
        }
    }

    private void sendDeactivationEmail(String email, String mobileNumber) {
        String emailContent = String.format("Your mobile number %s has been deactivated.", mobileNumber);

        // Prepare and send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mobile Number Deactivation Notification");
        message.setText(emailContent);

        try {
            // Send the email
            mailSender.send(message);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to Send the Mail", e);
            // Optionally, you can add more error handling or notifications here
        }
    }
}
