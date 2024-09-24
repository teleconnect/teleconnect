package com.teleconnect.service;

import com.teleconnect.dto.AdminDashboardResponse;
import com.teleconnect.entity.UserPlanAssignment;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import com.teleconnect.repository.EmailVerificationRepository;
import com.teleconnect.repository.RegistrationUserRepository;
import com.teleconnect.repository.ActivateMobileNumberRepository; // Import the new repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashboardService {

    @Autowired
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private RegistrationUserRepository registrationUserRepository;

    @Autowired
    private ActivateMobileNumberRepository activateMobileNumberRepository; // New repository for mobile numbers

    public AdminDashboardResponse getDashboardStatistics() {
        // Fetch all activated plans
        List<UserPlanAssignment> activatedPlans = userPlanAssignmentRepository.findAllByPlanStatus("activated");

        // Calculate total number of activated plans
        long currentActiveUsers = activatedPlans.size();

        // Calculate number of activated plans by type
        long usersWithBasicPlan = activatedPlans.stream().filter(plan -> "plan-basic".equals(plan.getPlanId())).count();
        long usersWithStandardPlan = activatedPlans.stream().filter(plan -> "plan-standard".equals(plan.getPlanId())).count();
        long usersWithPremiumPlan = activatedPlans.stream().filter(plan -> "plan-premium".equals(plan.getPlanId())).count();

        // Fetch the total number of email verified entries
        long usersWithVerifiedEmail = emailVerificationRepository.count();

        // Fetch the total number of registered users
        long totalRegisteredUsers = registrationUserRepository.count();

        // Get the count of distinct e-mails from user_plan_assignment
        long totalOnboardedUsers = userPlanAssignmentRepository.countDistinctEmails();

        // Get the first mobile_id from available_e_sim_numbers table and subtract 1
        long mobileNumbersAssigned = activateMobileNumberRepository.findFirstByOrderByMobileIdAsc()
                .map(mobileNumber -> mobileNumber.getMobileId() - 1) // Subtracting 1 from the smallest mobile_id
                .orElse(0L); // Default to 0 if no mobile numbers are found

        // Create and return response object with new field names
        return new AdminDashboardResponse(currentActiveUsers, usersWithBasicPlan, usersWithStandardPlan,
                usersWithPremiumPlan, usersWithVerifiedEmail, totalRegisteredUsers,
                totalOnboardedUsers, mobileNumbersAssigned); // Updated to new field names
    }
}
