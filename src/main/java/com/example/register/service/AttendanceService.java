package com.example.register.service;

import com.example.register.dto.AttendanceSummaryDto;
import com.example.register.dto.AttendanceTableDto;
import com.example.register.entity.Attendance;
import com.example.register.entity.Register;
import com.example.register.repository.AttendanceRepository;
import com.example.register.repository.RegisterRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final RegisterRepository registerRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, RegisterRepository registerRepository) {
        this.attendanceRepository = attendanceRepository;
        this.registerRepository = registerRepository;
    }

    // ✅ Punch In
    public String punchIn(String email) {
        Register user = registerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (attendanceRepository.findActiveByUser(user).isPresent()) {
            return "User already punched in!";
        }
        // 🚫 Prevent multiple punch-ins on the same date
        if (attendanceRepository.findByUserAndDate(user, LocalDate.now()).isPresent()) {
            return "You have already logged in today!";
        }

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(LocalDate.now());
        attendance.setPunchInTime(LocalDateTime.now());
        attendance.setActive(true);

        attendanceRepository.save(attendance);
        return "Punch in recorded for " + user.getEmail();
    }

    // ✅ Punch Out
    public String punchOut(String email) {
        Register user = registerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Attendance attendance = attendanceRepository.findActiveByUser(user)
                .orElseThrow(() -> new RuntimeException("No active punch-in found"));

        attendance.setPunchOutTime(LocalDateTime.now());
        attendance.setActive(false);
        attendanceRepository.save(attendance);

        return "Punch out recorded for " + user.getEmail();
    }

    // ✅ Auto Punch-Out after 12 hrs
    @Scheduled(fixedRate = 3600000) // every 1 hour
    public void autoPunchOut() {
        List<Attendance> activeRecords = attendanceRepository.findAllActive();
        for (Attendance a : activeRecords) {
            if (a.getPunchInTime().plusHours(12).isBefore(LocalDateTime.now())) {
                a.setPunchOutTime(a.getPunchInTime().plusHours(12));
                a.setActive(false);
                attendanceRepository.save(a);
            }
        }
    }

    public Attendance getTodayAttendance(String email) {
        Register user = registerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return attendanceRepository.findByUserAndDate(user, LocalDate.now())
                .orElse(null);
    }


    // ✅ Per-user summary
    public AttendanceSummaryDto getAttendanceSummary(Long userId, Integer month, Integer year) {
        Register user = registerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Attendance> records = attendanceRepository.findByUser(user);

        // Filter by month/year if provided
        if (month != null && year != null) {
            YearMonth ym = YearMonth.of(year, month);
            records = records.stream()
                    .filter(a -> a.getDate().getYear() == ym.getYear()
                            && a.getDate().getMonthValue() == ym.getMonthValue())
                    .toList();
        }

        long totalPresent = records.stream().filter(r -> r.getPunchInTime() != null).count();
        long totalAbsent = records.size() - totalPresent;
        long totalHoursWorked = records.stream()
                .filter(r -> r.getPunchOutTime() != null)
                .mapToLong(r -> Duration.between(r.getPunchInTime(), r.getPunchOutTime()).toHours())
                .sum();

        return new AttendanceSummaryDto(
                user.getId(),
                user.getEmail(),   // showing email instead of username
                totalPresent,
                totalAbsent,
                totalHoursWorked
        );
    }
    public List<AttendanceTableDto> getMyAttendanceTable(String username, Integer month, Integer year) {
        Register user = registerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Attendance> records = attendanceRepository.findByUser(user);

        if (month != null && year != null) {
            YearMonth ym = YearMonth.of(year, month);
            records = records.stream()
                    .filter(a -> a.getDate().getYear() == ym.getYear()
                            && a.getDate().getMonthValue() == ym.getMonthValue())
                    .toList();
        }

        return records.stream().map(a -> new AttendanceTableDto(
                a.getDate(),
                a.getPunchInTime(),
                a.getPunchOutTime(),
                (a.getPunchInTime() != null && a.getPunchOutTime() != null)
                        ? Duration.between(a.getPunchInTime(), a.getPunchOutTime()).toHours()
                        : 0,
                a.isActive()
        )).toList();
    }

    public AttendanceSummaryDto getMyAttendanceSummary(String username, Integer month, Integer year) {
        Register user = registerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Attendance> records = attendanceRepository.findByUser(user);

        if (month != null && year != null) {
            YearMonth ym = YearMonth.of(year, month);
            records = records.stream()
                    .filter(a -> a.getDate().getYear() == ym.getYear()
                            && a.getDate().getMonthValue() == ym.getMonthValue())
                    .toList();
        }

        long totalPresent = records.stream().filter(r -> r.getPunchInTime() != null).count();
        long totalAbsent = records.size() - totalPresent;
        long totalHoursWorked = records.stream()
                .filter(r -> r.getPunchOutTime() != null)
                .mapToLong(r -> Duration.between(r.getPunchInTime(), r.getPunchOutTime()).toHours())
                .sum();

        return new AttendanceSummaryDto(user.getId(), user.getUsername(), totalPresent, totalAbsent, totalHoursWorked);
    }


    // ✅ All users summary
    public List<AttendanceSummaryDto> getAllAttendanceSummaries(Integer month, Integer year) {
        List<Register> users = registerRepository.findAll();
        List<AttendanceSummaryDto> summaries = new ArrayList<>();

        for (Register user : users) {
            summaries.add(getAttendanceSummary(user.getId(), month, year));
        }
        return summaries;
    }
}
