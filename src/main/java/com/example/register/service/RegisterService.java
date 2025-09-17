package com.example.register.service;

import com.example.register.dto.ChangePasswordDto;
import com.example.register.dto.RegisterDto;
import com.example.register.dto.RegisterResponseDto;
import com.example.register.entity.Register;
import com.example.register.entity.Role;
import com.example.register.repository.RegisterRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    // 🔹 Update User
    public RegisterResponseDto updateUser(Long userId, RegisterResponseDto dto) {
        Optional<Register> userOpt = registerRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        Register user = userOpt.get();
        user.setUsername(dto.getUsername());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setRole(Role.USER);

        Register updated = registerRepository.save(user);

        return mapToResponseDto(updated);
    }

    // 🔹 Mapper method
    private RegisterResponseDto mapToResponseDto(Register user) {
        RegisterResponseDto dto = new RegisterResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole());
        return dto;
    }
    // In RegisterService
    public String changePassword(Long userId, ChangePasswordDto dto) {
        Register user = registerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // ✅ Check old password
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect!");
        }

        // ✅ Check confirmPassword
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match!");
        }

        // ✅ Encode new password
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setConfirmPassword(passwordEncoder.encode(dto.getConfirmPassword()));

        registerRepository.save(user);

        return "Password updated successfully!";
    }

}
