package com.teleconnect;

import com.teleconnect.scheduler.MobileNumberAvailabilityScheduler;
import com.teleconnect.service.MobileNumberAvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class MobileNumberAvailabilitySchedulerTest {

    @Mock
    private MobileNumberAvailabilityService mobileNumberAvailabilityService;

    @InjectMocks
    private MobileNumberAvailabilityScheduler mobileNumberAvailabilityScheduler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testScheduleNumberCheck() {
        // Act
        mobileNumberAvailabilityScheduler.scheduleNumberCheck();

        // Assert
        verify(mobileNumberAvailabilityService).checkAndAddMobileNumbers();
    }
}
