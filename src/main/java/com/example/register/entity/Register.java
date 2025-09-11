package com.example.register.entity;

import jakarta.persistence.*;
import lombok.Data;

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

    @Enumerated(EnumType.STRING)
    private Role role;   // ✅ new role field
}
