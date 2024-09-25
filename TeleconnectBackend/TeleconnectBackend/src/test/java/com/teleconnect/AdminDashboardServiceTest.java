package com.teleconnect;

import com.teleconnect.dto.AdminDashboardResponse;
import com.teleconnect.entity.UserPlanAssignment;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import com.teleconnect.service.AdminDashboardService;
import com.teleconnect.repository.EmailVerificationRepository;
import com.teleconnect.repository.RegistrationUserRepository;
import com.teleconnect.repository.ActivateMobileNumberRepository;
import com.teleconnect.entity.ActivateMobileNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AdminDashboardServiceTest {

    @Mock
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @Mock
    private RegistrationUserRepository registrationUserRepository;

    @Mock
    private ActivateMobileNumberRepository activateMobileNumberRepository;

    @InjectMocks
    private AdminDashboardService adminDashboardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDashboardStatistics() {
        // Setup mock data
        List<UserPlanAssignment> activatedPlans = Collections.singletonList(new UserPlanAssignment());
        activatedPlans.get(0).setPlanId("plan-basic");

        when(userPlanAssignmentRepository.findAllByPlanStatus("activated")).thenReturn(activatedPlans);
        when(emailVerificationRepository.count()).thenReturn(10L);
        when(registrationUserRepository.count()).thenReturn(20L);
        when(userPlanAssignmentRepository.countDistinctEmails()).thenReturn(15L);

        ActivateMobileNumber availableNumber = new ActivateMobileNumber();
        availableNumber.setMobileId(5L);
        when(activateMobileNumberRepository.findFirstByOrderByMobileIdAsc()).thenReturn(Optional.of(availableNumber));

        // Act
        AdminDashboardResponse response = adminDashboardService.getDashboardStatistics();

        // Assert
        assertEquals(1L, response.getCurrentActiveUsers());
        assertEquals(1L, response.getUsersWithBasicPlan());
        assertEquals(0L, response.getUsersWithStandardPlan());
        assertEquals(0L, response.getUsersWithPremiumPlan());
        assertEquals(10L, response.getUsersWithVerifiedEmail());
        assertEquals(20L, response.getTotalRegisteredUsers());
        assertEquals(15L, response.getTotalOnboardedUsers());
        assertEquals(4L, response.getMobileNumbersAssigned()); // Mobile ID - 1
    }
}
