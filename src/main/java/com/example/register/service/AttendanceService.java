package com.example.register.service;

import com.example.register.dto.AttendanceDto;
import com.example.register.dto.AttendanceSummaryDto;
import com.example.register.entity.Attendance;
import com.example.register.entity.Register;
import com.example.register.entity.Status;
import com.example.register.repository.AttendanceRepository;
import com.example.register.repository.RegisterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public String markAttendance(AttendanceDto dto) {
        Register user = registerRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(LocalDate.now());
        attendance.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));

        attendanceRepository.save(attendance);
        return "Attendance marked for " + user.getUsername();
    }

    public AttendanceSummaryDto getAttendanceSummary(Long userId) {
        Register user = registerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Attendance> records = attendanceRepository.findByUser(user);

        long totalPresent = records.stream().filter(a -> a.getStatus() == Status.PRESENT).count();
        long totalAbsent = records.stream().filter(a -> a.getStatus() == Status.ABSENT).count();

        return new AttendanceSummaryDto(
                user.getId(),
                user.getUsername(),
                totalPresent,
                totalAbsent,
                records.size()
        );
    }

    public List<AttendanceSummaryDto> getAllAttendanceSummaries() {
        List<Register> users = registerRepository.findAll();
        List<AttendanceSummaryDto> summaries = new ArrayList<>();

        for (Register user : users) {
            List<Attendance> records = attendanceRepository.findByUser(user);

            long totalPresent = records.stream().filter(a -> a.getStatus() == Status.PRESENT).count();
            long totalAbsent = records.stream().filter(a -> a.getStatus() == Status.ABSENT).count();

            AttendanceSummaryDto summary = new AttendanceSummaryDto(
                    user.getId(),
                    user.getUsername(),
                    totalPresent,
                    totalAbsent,
                    records.size()
            );

            summaries.add(summary);
        }
        return summaries;
    }

    public AttendanceSummaryDto getAttendanceSummary(Long userId, Integer month, Integer year) {
        Register user = registerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Attendance> records = attendanceRepository.findByUser(user);

        // ✅ Apply month/year filter if provided
        if (month != null && year != null) {
            YearMonth ym = YearMonth.of(year, month);
            records = records.stream()
                    .filter(a -> a.getDate().getYear() == ym.getYear()
                            && a.getDate().getMonthValue() == ym.getMonthValue())
                    .toList();
        }

        long totalPresent = records.stream().filter(a -> a.getStatus() == Status.PRESENT).count();
        long totalAbsent = records.stream().filter(a -> a.getStatus() == Status.ABSENT).count();

        return new AttendanceSummaryDto(
                user.getId(),
                user.getUsername(),
                totalPresent,
                totalAbsent,
                records.size()
        );
    }

    public List<AttendanceSummaryDto> getAllAttendanceSummaries(Integer month, Integer year) {
        List<Register> users = registerRepository.findAll();
        List<AttendanceSummaryDto> summaries = new ArrayList<>();

        for (Register user : users) {
            List<Attendance> records = attendanceRepository.findByUser(user);

            if (month != null && year != null) {
                YearMonth ym = YearMonth.of(year, month);
                records = records.stream()
                        .filter(a -> a.getDate().getYear() == ym.getYear()
                                && a.getDate().getMonthValue() == ym.getMonthValue())
                        .toList();
            }

            long totalPresent = records.stream().filter(a -> a.getStatus() == Status.PRESENT).count();
            long totalAbsent = records.stream().filter(a -> a.getStatus() == Status.ABSENT).count();

            AttendanceSummaryDto summary = new AttendanceSummaryDto(
                    user.getId(),
                    user.getUsername(),
                    totalPresent,
                    totalAbsent,
                    records.size()
            );

            summaries.add(summary);
        }
        return summaries;
    }


}
