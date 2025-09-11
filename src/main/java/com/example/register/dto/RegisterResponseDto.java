package com.example.register.dto;

import com.example.register.entity.Role;
import lombok.Data;

@Data
public class RegisterResponseDto {
    private Long id;
    private String username;
    private String phoneNumber;
    private String email;
    private String address;
    private Role role;   // if you want to show role in response
}

