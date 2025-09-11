package com.example.register.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    private Status status;  // PRESENT / ABSENT
}
