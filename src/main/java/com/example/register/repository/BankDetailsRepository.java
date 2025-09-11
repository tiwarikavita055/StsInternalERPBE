package com.example.register.repository;

import com.example.register.entity.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {
    BankDetails findByUserId(Long userId);
}
