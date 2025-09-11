package com.example.register.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_documents")
public class UserDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to Register user
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Register user;

    @Lob
    private byte[] tenthMarksheet;

    @Lob
    private byte[] twelfthMarksheet;

    @Lob
    private byte[] aadhaar;

    @Lob
    private byte[] pan;

    @Lob
    private byte[] resume;

    @Lob
    private byte[] photo;
}
