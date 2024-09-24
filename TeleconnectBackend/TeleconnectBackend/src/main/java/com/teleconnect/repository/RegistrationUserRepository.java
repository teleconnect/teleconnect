package com.teleconnect.repository;

import com.teleconnect.model.RegistrationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationUserRepository extends JpaRepository<RegistrationUser, Long> {
    boolean existsByEmail(String email);
    RegistrationUser findByEmail(String email);
    
    // Method to find the largest user ID
    Long findTopByOrderByIdDesc();
}
