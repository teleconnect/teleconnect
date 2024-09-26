package com.teleconnect.controller;

import com.teleconnect.dto.UserPlanInfoDTO;
import com.teleconnect.dto.UserPlanInfoRequestDTO;
import com.teleconnect.entity.UserPlanAssignment;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://44.201.255.255")
@RequestMapping("/api/user/plan")
public class UserPlanDetailsController {

    @Autowired
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @PostMapping("/status")
    public Object getUserPlanInfo(@RequestBody UserPlanInfoRequestDTO requestDTO) {
        // Retrieve the email from the request DTO
        String email = requestDTO.getEmail();

        // Fetch user plan assignments based on the email
        List<UserPlanAssignment> userPlanAssignments = userPlanAssignmentRepository.findAllByEmail(email);

        // Check if there are any plan assignments for the given email
        if (userPlanAssignments.isEmpty()) {
            throw new RuntimeException("User not found or no plan assigned.");
        }

        // Check if all plans are deactivated
        boolean allDeactivated = true;

        // Iterate over the user plan assignments
        for (UserPlanAssignment assignment : userPlanAssignments) {
            // Check if any plan is activated
            if ("activated".equalsIgnoreCase(assignment.getPlanStatus())) {
                allDeactivated = false;
                // Return the first activated plan information
                return new UserPlanInfoDTO(
                    assignment.getPlanId(),
                    assignment.getValidityStatus(),
                    assignment.getCreatedAt()
                );
            }
        }

        // If all plans are deactivated, return the message
        if (allDeactivated) {
            return "Mobile Number Deactivated";
        }

        // Return an error if no valid plan found (this case should not happen based on above logic)
        return new RuntimeException("User not found or no plan assigned.");
    }
}
