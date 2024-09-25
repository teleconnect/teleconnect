package com.teleconnect;

import com.teleconnect.entity.ActivateMobileNumber;
import com.teleconnect.repository.ActivateMobileNumberRepository;
import com.teleconnect.repository.GlobalSIMMobileNumberRepository;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import com.teleconnect.service.MobileNumberAvailabilityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

class MobileNumberAvailabilityServiceTest {

    @Mock
    private ActivateMobileNumberRepository activateMobileNumberRepository;

    @Mock
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @Mock
    private GlobalSIMMobileNumberRepository globalSIMMobileNumberRepository;

    @InjectMocks
    private MobileNumberAvailabilityService mobileNumberAvailabilityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckAndAddMobileNumbers() {
        // Mock the repository to return count less than 10
        when(activateMobileNumberRepository.count()).thenReturn(5L);
        when(activateMobileNumberRepository.findAll()).thenReturn(Collections.emptyList());
        when(userPlanAssignmentRepository.findAll()).thenReturn(Collections.emptyList());
        when(activateMobileNumberRepository.findMaxMobileId()).thenReturn(Optional.of(1L));
        when(globalSIMMobileNumberRepository.existsById(anyString())).thenReturn(false);

        mobileNumberAvailabilityService.checkAndAddMobileNumbers();

        // Verify that 5 new numbers were added
        verify(activateMobileNumberRepository, times(5)).save(any(ActivateMobileNumber.class));
    }

    @Test
    void testNoNewNumbersAddedWhenCountIsSufficient() {
        // Mock the repository to return count of 10
        when(activateMobileNumberRepository.count()).thenReturn(10L);

        mobileNumberAvailabilityService.checkAndAddMobileNumbers();

        // Verify that no new numbers were added
        verify(activateMobileNumberRepository, never()).save(any(ActivateMobileNumber.class));
    }
}
