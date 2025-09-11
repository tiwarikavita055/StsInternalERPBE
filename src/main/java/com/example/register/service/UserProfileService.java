package com.example.register.service;

import com.example.register.dto.UserFullProfileDTO;
import com.example.register.entity.BankDetails;
import com.example.register.entity.Register;
import com.example.register.entity.UserDocument;
import com.example.register.repository.BankDetailsRepository;
import com.example.register.repository.RegisterRepository;
import com.example.register.repository.UserDocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final RegisterRepository registerRepository;
    private final UserDocumentRepository documentRepository;
    private final BankDetailsRepository bankDetailsRepository;

    public UserProfileService(RegisterRepository registerRepository,
                              UserDocumentRepository documentRepository,
                              BankDetailsRepository bankDetailsRepository) {
        this.registerRepository = registerRepository;
        this.documentRepository = documentRepository;
        this.bankDetailsRepository = bankDetailsRepository;
    }

    public UserFullProfileDTO getUserFullProfile(Long userId) {
        Register user = registerRepository.findById(userId).orElse(null);
        UserDocument documents = documentRepository.findByUserId(userId);
        BankDetails bankDetails = bankDetailsRepository.findByUserId(userId);

        UserFullProfileDTO dto = new UserFullProfileDTO();
        dto.setUser(user);
        dto.setDocuments(documents);
        dto.setBankDetails(bankDetails);

        return dto;
    }
}
