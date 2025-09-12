package com.example.register.repository;

import com.example.register.entity.Attendance;
import com.example.register.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByUser(Register user);

    @Query("SELECT a FROM Attendance a WHERE a.user = :user AND a.active = true")
    Optional<Attendance> findActiveByUser(Register user);

    @Query("SELECT a FROM Attendance a WHERE a.active = true")
    List<Attendance> findAllActive();
}
