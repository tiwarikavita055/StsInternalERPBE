package com.example.register.controller;

import com.example.register.dto.RegisterDto;
import com.example.register.dto.RegisterResponseDto;
import com.example.register.service.RegisterService;
import jakarta.validation.Valid;
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

}
