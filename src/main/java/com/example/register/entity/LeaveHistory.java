package com.example.register.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class LeaveHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long employeeId;

    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;
    private String status;
    private  LocalDate applyDate;

    // Getters and Setters
}
