package com.teleconnect.repository;

import com.teleconnect.entity.GlobalSIMMobileNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSIMMobileNumberRepository extends JpaRepository<GlobalSIMMobileNumber, String> {
    // Additional query methods can be defined here if needed
}
