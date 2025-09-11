package com.example.register.repository;

import com.example.register.entity.Attendance;
import com.example.register.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser(Register user);
    List<Attendance> findByDate(LocalDate date);
}
