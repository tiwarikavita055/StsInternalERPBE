package com.example.register.repository;

import com.example.register.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStatus(String status);

    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId,String status);
}
