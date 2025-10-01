package com.example.register.controller;

import com.example.register.entity.Attendance;
import com.example.register.entity.Register;
import com.example.register.entity.Status;
import com.example.register.repository.AttendanceRepository;
import com.example.register.repository.RegisterRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    private final RegisterRepository registerRepository;
    private final AttendanceRepository attendanceRepository;
    public AdminUserController(RegisterRepository registerRepository, AttendanceRepository attendanceRepository) {
        this.registerRepository = registerRepository;
        this.attendanceRepository = attendanceRepository;
    }

    // ✅ API for Admin to set/update joining date
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/{userId}/joining-date")
    public Register setJoiningDate(
            @PathVariable Long userId,
            @RequestParam String joiningDate // in yyyy-MM-dd format
    ) {
        Register user = registerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        user.setJoiningDate(LocalDate.parse(joiningDate));
        return registerRepository.save(user);
    }
    // ✅ Admin punches in for an employee
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/attendance/{employeeId}/punch-in")
    public Attendance adminPunchIn(
            @PathVariable Long employeeId,
            @RequestParam(required = false) String punchTime // optional, default now
    ) {
        Register employee = registerRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));

        LocalDate today = LocalDate.now();
        LocalDateTime punchDateTime = (punchTime != null)
                ? LocalDateTime.parse(punchTime)  // expects ISO format yyyy-MM-ddTHH:mm:ss
                : LocalDateTime.now();

        // Find existing attendance or create new
        Attendance attendance = attendanceRepository.findByUserAndDate(employee, today)
                .orElse(new Attendance());

        attendance.setUser(employee);  // ✅ pass Register object
        attendance.setDate(today);
        attendance.setPunchInTime(punchDateTime);

        // Set status: Halfday if after 11 AM
        if (punchDateTime.toLocalTime().isAfter(LocalTime.of(11, 0))) {
            attendance.setStatus(Status.HALFDAY);
        } else {
            attendance.setStatus(Status.PRESENT);
        }

        attendance.setActive(true); // mark as punched in
        attendance.setAbsent(false);

        return attendanceRepository.save(attendance);
    }

}
