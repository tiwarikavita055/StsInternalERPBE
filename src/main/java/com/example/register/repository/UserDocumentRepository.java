package com.example.register.repository;

import com.example.register.entity.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDocumentRepository extends JpaRepository<UserDocument, Long> {
//    UserDocument findByUserId(Long userId);
    @Query("SELECT d FROM UserDocument d WHERE d.user.id = :userId")
    UserDocument findByUserId(@Param("userId") Long userId);

}
