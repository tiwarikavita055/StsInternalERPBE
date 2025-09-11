package com.example.register.dto;

import com.example.register.entity.BankDetails;
import com.example.register.entity.Register;
import com.example.register.entity.UserDocument;
import lombok.Data;

@Data
public class UserFullProfileDTO {
    private Register user;
    private UserDocument documents;
    private BankDetails bankDetails;
}
