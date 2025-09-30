package com.example.register.controller;


import com.example.register.dto.LeaveRequestsViewDto;
import com.example.register.entity.LeaveBalance;
import com.example.register.entity.LeaveHistory;
import com.example.register.entity.LeaveRequest;
import com.example.register.repository.LeaveHistoryRepository;
import com.example.register.service.LeaveBalanceService;
import com.example.register.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {



    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @Autowired
    private LeaveHistoryRepository leaveHistoryRepository;



    // Approve a leave request
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/approve-leave/{requestId}")
    public LeaveRequest approveLeave(@PathVariable Long requestId) {
        return leaveRequestService.approveLeave(requestId);
    }

    // Reject a leave request
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/reject-leave/{requestId}")
    public LeaveRequest rejectLeave(@PathVariable Long requestId) {
        return leaveRequestService.rejectLeave(requestId);
    }

    // View all leave requests
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/leave-requests")
    public List<LeaveRequestsViewDto> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests().stream().filter(leaveRequest -> leaveRequest.getStatus().equals("PENDING")).toList();
    }

    // Add leave balance for an employee
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/{employeeId}/add-leave-balance")
    public LeaveBalance addLeaveBalance(
            @PathVariable Long employeeId,
            @RequestBody LeaveBalance leaveBalance) {

        leaveBalance.setEmployeeId(employeeId);
        return leaveBalanceService.addLeaveBalance(leaveBalance);
    }

    // Deduct leave balance for an employee
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/{employeeId}/deduct-leave-balance")
    public LeaveBalance deductLeaveBalance(
            @PathVariable Long employeeId,
            @RequestBody LeaveBalance leaveBalance) {

        leaveBalance.setEmployeeId(employeeId);
        return leaveBalanceService.deductLeaveBalance(leaveBalance);
    }

    // View leave history for all employees
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/leave-history")
    public List<LeaveHistory> getLeaveHistory() {
        return leaveHistoryRepository.findAll();
    }


    // Apply for a leave
    @PostMapping("/{employeeId}/apply-leave")
    public LeaveRequest applyLeave(
            @PathVariable Long employeeId,
            @RequestBody LeaveRequest leaveRequest) {
        leaveRequest.setEmployeeId(employeeId);
        return leaveRequestService.applyLeave(leaveRequest);
    }

    // View leave balances for an employee
    @GetMapping("/{employeeId}/leave-balances")
    public List<LeaveBalance> getLeaveBalances(@PathVariable Long employeeId) {
        //   Long employee = employeeService.getEmployeeById(employeeId);
        return leaveBalanceService.getLeaveBalancesForEmployee(employeeId);
    }

    // View leave history for an employee
    @GetMapping("/{employeeId}/leave-history")
    public List<LeaveHistory> getLeaveHistory(@PathVariable Long employeeId) {
        // Long employee = employeeService.getEmployeeById(employeeId);
        return leaveHistoryRepository.findByEmployeeId(employeeId);
    }

    @GetMapping("/employee/{employeeId}/leave-requests")
    public List<LeaveRequest> getEmployeeLeaveRequests(@PathVariable Long employeeId) {
        return leaveRequestService.getEmployeeLeaveRequests(employeeId);
    }



}
