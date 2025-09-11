package com.example.register.controller;

import com.example.register.dto.UserFullProfileDTO;
import com.example.register.security.JwtUtil;
import com.example.register.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileService profileService;
    private final JwtUtil jwtUtil;

    public UserProfileController(UserProfileService profileService, JwtUtil jwtUtil) {
        this.profileService = profileService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/me")
    public UserFullProfileDTO getMyProfile(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        Long userId = jwtUtil.extractUserId(jwt);
        return profileService.getUserFullProfile(userId);
    }

    @GetMapping("/admin/{userId}")
    public UserFullProfileDTO getAnyUserProfile(@PathVariable Long userId) {
        return profileService.getUserFullProfile(userId);
    }
}
