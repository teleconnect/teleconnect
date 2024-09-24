package com.teleconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teleconnect.entity.EmailVerification;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    // Method to find an EmailVerification by email
    EmailVerification findByEmail(String email);

    // Method to check if an EmailVerification exists by email
    boolean existsByEmail(String email);
}
