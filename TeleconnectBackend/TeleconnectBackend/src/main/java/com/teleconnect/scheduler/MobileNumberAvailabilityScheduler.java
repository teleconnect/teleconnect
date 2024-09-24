package com.teleconnect.scheduler;

import com.teleconnect.service.MobileNumberAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MobileNumberAvailabilityScheduler {

    @Autowired
    private MobileNumberAvailabilityService mobileNumberAvailabilityService;

    // Schedule to run every minute
    @Scheduled(fixedRate = 60000)
    public void scheduleNumberCheck() {
        mobileNumberAvailabilityService.checkAndAddMobileNumbers();
    }
}