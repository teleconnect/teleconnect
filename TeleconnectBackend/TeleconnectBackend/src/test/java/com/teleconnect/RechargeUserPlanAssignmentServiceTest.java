package com.teleconnect;

import com.teleconnect.entity.UserPlanAssignment;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import com.teleconnect.service.RechargeUserPlanAssignmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RechargeUserPlanAssignmentServiceTest {

    @Mock
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private RechargeUserPlanAssignmentService rechargeUserPlanAssignmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateValidityStatus_UserNotFound() {
        // Mock empty result for user not found
        when(userPlanAssignmentRepository.findAllByEmail("test@example.com")).thenReturn(Collections.emptyList());

        // Call the method and assert the result
        String result = rechargeUserPlanAssignmentService.updateValidityStatus("test@example.com", "plan-basic");
        assertEquals("User with the given email not found.", result);
    }

    @Test
    public void testUpdateValidityStatus_RechargeSuccessful() {
        // Mock data for an activated user
        UserPlanAssignment assignment = new UserPlanAssignment();
        assignment.setPlanStatus("activated");
        assignment.setValidityStatus(Timestamp.from(Instant.now()));

        when(userPlanAssignmentRepository.findAllByEmail("test@example.com")).thenReturn(List.of(assignment));

        // Mock mail sender with explicit SimpleMailMessage
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Call the method and assert the result
        String result = rechargeUserPlanAssignmentService.updateValidityStatus("test@example.com", "plan-basic");
        assertEquals("Recharge Successful !! New Plan Activated.", result);
    }

}
