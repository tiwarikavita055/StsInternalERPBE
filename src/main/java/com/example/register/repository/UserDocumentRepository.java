package com.example.register.repository;

import com.example.register.entity.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDocumentRepository extends JpaRepository<UserDocument, Long> {
    UserDocument findByUserId(Long userId);
}
