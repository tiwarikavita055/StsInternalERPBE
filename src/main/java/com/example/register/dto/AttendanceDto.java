package com.example.register.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendanceDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Status is required")
    private String status; // "PRESENT" or "ABSENT"
}
