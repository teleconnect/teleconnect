package com.teleconnect.repository;

import com.teleconnect.entity.ActivateMobileNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface ActivateMobileNumberRepository extends JpaRepository<ActivateMobileNumber, Long> {

    // Method to find the first available mobile number
    Optional<ActivateMobileNumber> findFirstByOrderByMobileIdAsc();

    // Optional: Method to find a mobile number by its ID
    Optional<ActivateMobileNumber> findByMobileId(Long mobileId);

    // Optional: Method to delete a mobile number by its ID
    void deleteByMobileId(Long mobileId);

    // Method to find the maximum mobile_id
    @Query("SELECT MAX(m.mobileId) FROM ActivateMobileNumber m")
    Optional<Long> findMaxMobileId();
}
