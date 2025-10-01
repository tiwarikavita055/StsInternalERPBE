package com.example.register.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "register")
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String phoneNumber;
    private String email;
    private String address;
    private String password;
    private String confirmPassword;
    private LocalDate joiningDate;
    @Enumerated(EnumType.STRING)
    private Role role;   // ✅ new role field
}
