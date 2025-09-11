package com.example.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceSummaryDto {
    private Long userId;
    private String username;
    private long totalPresent;
    private long totalAbsent;
    private long totalDays;
}
