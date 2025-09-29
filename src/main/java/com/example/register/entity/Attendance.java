package com.example.register.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to user (Register)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Register user;

    private LocalDate date;

    private LocalDateTime punchInTime;

    private LocalDateTime punchOutTime;
    private boolean absent = false;

    private boolean active; // true = punched in, false = punched out
}
