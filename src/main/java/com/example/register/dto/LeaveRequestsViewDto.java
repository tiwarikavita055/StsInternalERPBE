package com.example.register.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestsViewDto {

    private Long id;


    private Long employeeId;


    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType; // Sick, Earned, Casual
    private String status; // PENDING, APPROVED, REJECTED
    private String description;
    private  String empName;
    private  LocalDate applyDate;

}
