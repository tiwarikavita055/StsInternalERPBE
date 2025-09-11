package com.example.register.service;

import com.example.register.dto.RegisterDto;
import com.example.register.dto.RegisterResponseDto;
import com.example.register.entity.Register;
import com.example.register.entity.Role;
import com.example.register.repository.RegisterRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final RegisterRepository registerRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(RegisterRepository registerRepository,
                           PasswordEncoder passwordEncoder) {
        this.registerRepository = registerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponseDto registerUser(RegisterDto dto) {
        Register entity = new Register();
        entity.setUsername(dto.getUsername());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());

        // ✅ Encrypt password
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setConfirmPassword(passwordEncoder.encode(dto.getConfirmPassword()));

        entity.setRole(Role.USER); // default role

        Register saved = registerRepository.save(entity);

        // ✅ Build response without exposing password
        RegisterResponseDto response = new RegisterResponseDto();
        response.setId(saved.getId());
        response.setUsername(saved.getUsername());
        response.setPhoneNumber(saved.getPhoneNumber());
        response.setEmail(saved.getEmail());
        response.setAddress(saved.getAddress());
        response.setRole(saved.getRole());

        return response;
    }
}
