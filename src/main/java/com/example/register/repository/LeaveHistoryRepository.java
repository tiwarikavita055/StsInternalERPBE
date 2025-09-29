package com.example.register.repository;

import com.example.register.entity.LeaveHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveHistoryRepository extends JpaRepository<LeaveHistory, Long> {
    List<LeaveHistory> findByEmployeeId(Long employee);
}
