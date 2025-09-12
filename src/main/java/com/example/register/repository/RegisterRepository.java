package com.example.register.repository;

import com.example.register.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Long> {
    Optional<Register> findByEmail(String email);  // ✅ correct

    Optional<Register> findByUsername(String username);
}
