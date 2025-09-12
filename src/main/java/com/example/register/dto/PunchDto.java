package com.example.register.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PunchDto {
    @NotNull(message = "Action is required")
    private String action; // "IN" or "OUT"
}
