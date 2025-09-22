package com.example.register.controller;

import com.example.register.dto.ChangePasswordDto;
import com.example.register.dto.RegisterDto;
import com.example.register.dto.RegisterResponseDto;
import com.example.register.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

//    @PostMapping
//    public RegisterDto register(@Valid @RequestBody RegisterDto dto) {
//        return registerService.registerUser(dto);
//    }

    @PostMapping
    public RegisterResponseDto register(@Valid @RequestBody RegisterDto dto) {
        return registerService.registerUser(dto);
    }
    // ✅ Update own profile (token-based, no userId required)
    @PutMapping("/edit/me")
    public RegisterResponseDto updateMyUser(@RequestBody RegisterResponseDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // email comes from JWT subject
        return registerService.updateUserByEmail(email, dto);
    }

    // ✅ Change own password (token-based)
    @PutMapping("/change-password/me")
    public String changeMyPassword(@RequestBody ChangePasswordDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return registerService.changePasswordByEmail(email, dto);
    }
}
