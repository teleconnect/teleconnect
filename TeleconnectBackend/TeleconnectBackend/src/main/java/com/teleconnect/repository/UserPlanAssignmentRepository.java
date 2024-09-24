package com.teleconnect.repository;

import com.teleconnect.entity.UserPlanAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserPlanAssignmentRepository extends JpaRepository<UserPlanAssignment, Long> {

    Optional<UserPlanAssignment> findByEmail(String email);
    
    List<UserPlanAssignment> findAllByEmail(String email);
    
    List<UserPlanAssignment> findAllByEmailAndPlanStatus(String email, String planStatus);

    List<UserPlanAssignment> findAllByPlanStatus(String planStatus);

    long countByEmailAndPlanStatus(String email, String planStatus);
    
    // Method to find all assignments with validity status
    List<UserPlanAssignment> findAllByValidityStatus(Timestamp validityStatus);
    
    // Method to check if an email already exists
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(DISTINCT email) FROM UserPlanAssignment")
    long countDistinctEmails();
}
