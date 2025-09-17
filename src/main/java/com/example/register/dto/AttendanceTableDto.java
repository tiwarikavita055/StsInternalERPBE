package com.example.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceTableDto {
    private LocalDate date;
    private LocalDateTime punchInTime;
    private LocalDateTime punchOutTime;
    private long workingHours;   // total hours worked that day
    private boolean active;      // true = punched in but not out yet
}
