package com.example.register.service;

import com.example.register.dto.LeaveRequestsViewDto;
import com.example.register.entity.LeaveBalance;
import com.example.register.entity.LeaveHistory;
import com.example.register.entity.LeaveRequest;
import com.example.register.repository.LeaveHistoryRepository;
import com.example.register.repository.LeaveRequestRepository;
import com.example.register.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private LeaveHistoryRepository leaveHistoryRepository;

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @Autowired
    private RegisterRepository internalEmployeeRepo;

    public LeaveRequest applyLeave(LeaveRequest leaveRequest) {
        // Validate leave balance
        LeaveBalance balance = leaveBalanceService.getLeaveBalance(
                leaveRequest.getEmployeeId(),
                leaveRequest.getLeaveType()
        );

        int requestedDays = (int) (leaveRequest.getEndDate().toEpochDay() - leaveRequest.getStartDate().toEpochDay() + 1);

        if (balance.getBalance() < requestedDays) {
            throw new RuntimeException("Insufficient leave balance for " + leaveRequest.getLeaveType());
        }

        leaveRequest.setStatus("PENDING");
        leaveRequest.setApplyDate(LocalDate.now());
        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest approveLeave(Long requestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found"));

        leaveRequest.setStatus("APPROVED");
        leaveBalanceService.deductLeave(
                leaveRequest.getEmployeeId(),
                leaveRequest.getLeaveType(),
                (int) (leaveRequest.getEndDate().toEpochDay() - leaveRequest.getStartDate().toEpochDay() + 1)
        );

        saveLeaveHistory(leaveRequest, "APPROVED");
        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest rejectLeave(Long requestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found"));

        leaveRequest.setStatus("REJECTED");
        saveLeaveHistory(leaveRequest, "REJECTED");
        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequestsViewDto> getAllLeaveRequests() {

  List<LeaveRequestsViewDto>  leaveRequestsViewDtoList = new ArrayList<>();
       List<LeaveRequest> als = leaveRequestRepository.findAll();
       for(LeaveRequest r : als ) {
           LeaveRequestsViewDto s = new LeaveRequestsViewDto();
           s.setId(r.getId());
           s.setEmpName(internalEmployeeRepo.findById(r.getEmployeeId()).get().getUsername());
           s.setEmployeeId(r.getEmployeeId());
           s.setStatus(r.getStatus());
           s.setLeaveType(r.getLeaveType());
           s.setEndDate(r.getEndDate());
           s.setStartDate(r.getStartDate());
           s.setDescription(r.getDescription());
           s.setApplyDate(r.getApplyDate());
           leaveRequestsViewDtoList.add(s);

       }
        return leaveRequestsViewDtoList;
    }

    public List<LeaveRequest> getEmployeeLeaveRequests(Long employeeId) {
        return leaveRequestRepository.findByEmployeeIdAndStatus(employeeId, "PENDING");
    }

    private void saveLeaveHistory(LeaveRequest leaveRequest, String status) {
        LeaveHistory leaveHistory = new LeaveHistory();
        leaveHistory.setEmployeeId(leaveRequest.getEmployeeId());
        leaveHistory.setStartDate(leaveRequest.getStartDate());
        leaveHistory.setEndDate(leaveRequest.getEndDate());
        leaveHistory.setLeaveType(leaveRequest.getLeaveType());
        leaveHistory.setStatus(status);
        leaveHistory.setApplyDate(leaveRequest.getApplyDate());
        leaveHistoryRepository.save(leaveHistory);
    }
}
