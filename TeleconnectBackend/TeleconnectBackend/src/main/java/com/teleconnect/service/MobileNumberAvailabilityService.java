package com.teleconnect.service;

import com.teleconnect.entity.ActivateMobileNumber;
import com.teleconnect.repository.ActivateMobileNumberRepository;
import com.teleconnect.repository.GlobalSIMMobileNumberRepository;
import com.teleconnect.repository.UserPlanAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class MobileNumberAvailabilityService {

    @Autowired
    private ActivateMobileNumberRepository activateMobileNumberRepository;

    @Autowired
    private UserPlanAssignmentRepository userPlanAssignmentRepository;

    @Autowired
    private GlobalSIMMobileNumberRepository globalSIMMobileNumberRepository; // Injected repository for all_mobile_numbers

    private static final int REQUIRED_NUMBERS = 10; // Required number of mobile numbers in the table

    @Transactional
    public void checkAndAddMobileNumbers() {
        // Get count of existing mobile numbers in the available_e_sim_numbers table
        long currentCount = activateMobileNumberRepository.count();

        // Calculate how many new numbers to add to make the total count 10
        int numbersToAdd = REQUIRED_NUMBERS - (int) currentCount;

        if (numbersToAdd > 0) {
            addNewMobileNumbers(numbersToAdd);
        }
    }

    private void addNewMobileNumbers(int count) {
        Set<String> existingNumbers = new HashSet<>();

        // Fetch all existing numbers in available_e_sim_numbers and user_plan_assignment tables
        activateMobileNumberRepository.findAll().forEach(number -> existingNumbers.add(number.getMobileNumber()));
        userPlanAssignmentRepository.findAll().forEach(assignment -> existingNumbers.add(assignment.getMobileNumber()));

        // Retrieve the maximum current mobile_id in available_e_sim_numbers
        Long currentMaxId = activateMobileNumberRepository.findMaxMobileId().orElse(0L);
        Long newMobileId = currentMaxId + 1; // Start incrementing from the next id

        Random random = new Random();

        while (count > 0) {
            // Generate random mobile number in the format 9xxxxxxxxx
            String newMobileNumber = "9" + (random.nextInt(900000000) + 100000000);

            // Check if the number exists in the all_mobile_numbers table
            boolean existsInAllMobileNumbers = globalSIMMobileNumberRepository.existsById(newMobileNumber);

            // If the number exists in all_mobile_numbers, continue generating
            if (existsInAllMobileNumbers) {
                continue; // Skip adding this number and try a new one
            }

            // Check if number already exists in existing numbers set
            if (!existingNumbers.contains(newMobileNumber)) {
                // Save to the database with incremented mobile_id
                ActivateMobileNumber newNumber = new ActivateMobileNumber();
                newNumber.setMobileId(newMobileId);
                newNumber.setMobileNumber(newMobileNumber);
                activateMobileNumberRepository.save(newNumber);

                // Add to existing numbers set to avoid duplicates in the same batch
                existingNumbers.add(newMobileNumber);

                // Increment the mobile_id for the next entry
                newMobileId++;

                count--; // Decrease the count
            }
        }
    }
}
