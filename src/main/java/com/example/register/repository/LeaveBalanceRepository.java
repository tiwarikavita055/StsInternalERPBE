package com.example.register.repository;

import com.example.register.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    List<LeaveBalance> findByEmployeeId(Long employee);

    Optional<LeaveBalance> findByEmployeeIdAndLeaveType(Long employee, String leaveType);
}
