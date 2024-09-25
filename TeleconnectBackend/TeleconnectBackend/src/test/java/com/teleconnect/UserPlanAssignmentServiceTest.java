package com.teleconnect;

import com.teleconnect.entity.ActivateMobileNumber;
import com.teleconnect.entity.UserPlanAssignment;
import com.teleconnect.repository.ActivateMobileNumberRepository;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import com.teleconnect.service.UserPlanAssignmentService;
import com.teleconnect.repository.RegistrationUserRepository;
import com.teleconnect.repository.GlobalSIMMobileNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserPlanAssignmentServiceTest {

    @Mock
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @Mock
    private ActivateMobileNumberRepository activateMobileNumberRepository;

    @Mock
    private RegistrationUserRepository registrationUserRepository;

    @Mock
    private GlobalSIMMobileNumberRepository globalSIMMobileNumberRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private UserPlanAssignmentService userPlanAssignmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignMobileNumber_Success() {
        // Setup
        String email = "test@example.com";
        String planId = "plan-basic";
        String eId = "12345678901234561234567890123456";

        ActivateMobileNumber availableNumber = new ActivateMobileNumber();
        availableNumber.setMobileNumber("9123456789");
        availableNumber.setMobileId(1L);

        when(registrationUserRepository.existsByEmail(email)).thenReturn(true);
        when(userPlanAssignmentRepository.findAllByEmail(email)).thenReturn(Collections.emptyList());
        when(activateMobileNumberRepository.findFirstByOrderByMobileIdAsc()).thenReturn(Optional.of(availableNumber));

        // Act
        UserPlanAssignment result = userPlanAssignmentService.assignMobileNumber(email, eId, planId);

        // Assert
        assertEquals(email, result.getEmail());
        assertEquals(planId, result.getPlanId());
        assertEquals("9123456789", result.getMobileNumber());
        assertEquals("activated", result.getPlanStatus());
    }
}
