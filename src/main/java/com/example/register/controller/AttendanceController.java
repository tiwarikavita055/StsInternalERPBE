package com.example.register.controller;

import com.example.register.dto.AttendanceDto;
import com.example.register.dto.AttendanceSummaryDto;
import com.example.register.entity.Attendance;
import com.example.register.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ✅ Mark attendance
    @PostMapping("/mark")
    public String markAttendance(@Valid @RequestBody AttendanceDto dto) {
        return attendanceService.markAttendance(dto);
    }

//    // ✅ Get summary for one user
//    @GetMapping("/summary/{userId}")
//    public AttendanceSummaryDto getSummary(@PathVariable Long userId) {
//        return attendanceService.getAttendanceSummary(userId);
//    }
//
//    // ✅ Get summary for all users
//    @GetMapping("/summary")
//    public List<AttendanceSummaryDto> getAllSummaries() {
//        return attendanceService.getAllAttendanceSummaries();
//    }



    // ✅ Per-user summary with optional filters
    @GetMapping("/summary/{userId}")
    public AttendanceSummaryDto getUserSummary(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        return attendanceService.getAttendanceSummary(userId, month, year);
    }

    // ✅ All users summary with optional filters
    @GetMapping("/summary")
    public List<AttendanceSummaryDto> getAllSummaries(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        return attendanceService.getAllAttendanceSummaries(month, year);
    }


}
