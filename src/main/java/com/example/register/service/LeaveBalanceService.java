package com.example.register.service;

import com.example.register.entity.InternalEmployeees;
import com.example.register.entity.LeaveBalance;
import com.example.register.repository.InternalEmployeeRepo;
import com.example.register.repository.LeaveBalanceRepository;
import com.example.register.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveBalanceService {

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;


    @Autowired
    private InternalEmployeeRepo employeeRepo;

    public void addDefaultLeave(Long employee) {
        List<LeaveBalance> balances = leaveBalanceRepository.findByEmployeeId(employee);
        for (LeaveBalance balance : balances) {
            switch (balance.getLeaveType()) {
                case "Sick":
                    balance.setBalance(balance.getBalance() + 2);
                    break;
                case "Earned":
                    balance.setBalance(balance.getBalance() + 1);
                    break;
                case "Casual":
                    balance.setBalance(balance.getBalance() + 1);
                    break;
            }
        }
        leaveBalanceRepository.saveAll(balances);
    }


    public List<LeaveBalance> getLeaveBalancesForEmployee(Long employee) {

        return leaveBalanceRepository.findByEmployeeId(employee);
    }

    public LeaveBalance getLeaveBalance(Long employee, String leaveType) {
        InternalEmployeees eee = employeeRepo.findById(employee).orElseThrow(() ->   new ResourceNotFoundException("Resource with ID " + employee + " not found"));
        return leaveBalanceRepository.findByEmployeeIdAndLeaveType(employee, leaveType)
                .orElseGet(() -> {
                    // Create and return an empty LeaveBalance object
                    LeaveBalance emptyLeaveBalance = new LeaveBalance();
                    emptyLeaveBalance.setEmployeeId(employee);
                    emptyLeaveBalance.setLeaveType(leaveType);
                    emptyLeaveBalance.setBalance(0); // Default balance
                    return emptyLeaveBalance;
                });
      //  return leaveBalanceRepository.findByEmployeeAndLeaveType(employee, leaveType);
    }

    public LeaveBalance addLeaveBalance(LeaveBalance leaveBalance) {
        LeaveBalance existingBalance = getLeaveBalance(
                leaveBalance.getEmployeeId(), leaveBalance.getLeaveType());
        existingBalance.setBalance(existingBalance.getBalance() + leaveBalance.getBalance());
        return leaveBalanceRepository.save(existingBalance);
    }

    public LeaveBalance deductLeaveBalance(LeaveBalance leaveBalance) {

        LeaveBalance existingBalance = getLeaveBalance(
                leaveBalance.getEmployeeId(), leaveBalance.getLeaveType());
        if (existingBalance.getBalance() < leaveBalance.getBalance()) {
            throw new RuntimeException("Insufficient leave balance for " + leaveBalance.getLeaveType());
        }
        existingBalance.setBalance(existingBalance.getBalance() - leaveBalance.getBalance());
        return leaveBalanceRepository.save(existingBalance);
    }

    public void deductLeave(Long employee, String leaveType, int days) {
        LeaveBalance balance = getLeaveBalance(employee, leaveType);
        if (balance.getBalance() < days) {
            throw new RuntimeException("Insufficient leave balance for " + leaveType);
        }
        balance.setBalance(balance.getBalance() - days);
        leaveBalanceRepository.save(balance);
    }

}
