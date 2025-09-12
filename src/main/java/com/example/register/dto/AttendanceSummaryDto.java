package com.example.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceSummaryDto {
    private Long userId;
    private String username;
    private long totalPresentDays;
    private long totalAbsentDays;
    private long totalHoursWorked;
}
