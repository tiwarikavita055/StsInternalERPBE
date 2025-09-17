package com.example.register.controller;

import com.example.register.dto.AttendanceSummaryDto;
import com.example.register.dto.AttendanceTableDto;
import com.example.register.dto.PunchDto;
import com.example.register.entity.Attendance;
import com.example.register.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }



//    @PostMapping("/punch")
//    public String punch(@Valid @RequestBody PunchDto dto) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName(); // comes from JWT subject (usually email/username)
//
//        if ("IN".equalsIgnoreCase(dto.getAction())) {
//            return attendanceService.punchIn(username);
//        } else if ("OUT".equalsIgnoreCase(dto.getAction())) {
//            return attendanceService.punchOut(username);
//        } else {
//            return "Invalid action. Use IN or OUT.";
//        }
//    }
// ✅ Punch In
@PostMapping("/punch-in")
public String punchIn() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getName(); // comes from JWT subject (email)
    return attendanceService.punchIn(email);
}

    // ✅ Punch Out
    @PostMapping("/punch-out")
    public String punchOut() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // comes from JWT subject (email)
        return attendanceService.punchOut(email);
    }
    // ✅ Detailed attendance table for logged-in user
    @GetMapping("/table/me")
    public List<AttendanceTableDto> getMyAttendanceTable(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // comes from JWT subject (email)

        return attendanceService.getMyAttendanceTable(email, month, year);
    }




    // ✅ Per-user summary
    // ✅ Per-user summary (uses JWT token instead of userId)
    @GetMapping("/summary/me")
    public AttendanceSummaryDto getMySummary(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // comes from JWT subject (email)
        return attendanceService.getMyAttendanceSummary(email, month, year);
    }


    // ✅ All users summary
    @GetMapping("/summary")
    public List<AttendanceSummaryDto> getAllSummaries(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        return attendanceService.getAllAttendanceSummaries(month, year);
    }
    @GetMapping("/today")
    public Attendance getTodayAttendance() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // comes from JWT subject (email)

        return attendanceService.getTodayAttendance(email);
    }


}
