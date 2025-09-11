package com.example.register.service;

import com.example.register.entity.Register;
import com.example.register.entity.UserDocument;
import com.example.register.repository.RegisterRepository;
import com.example.register.repository.UserDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserDocumentService {

    private final UserDocumentRepository documentRepository;
    private final RegisterRepository registerRepository;

    public UserDocumentService(UserDocumentRepository documentRepository, RegisterRepository registerRepository) {
        this.documentRepository = documentRepository;
        this.registerRepository = registerRepository;
    }

    public String uploadDocuments(Long userId,
                                  MultipartFile tenth,
                                  MultipartFile twelfth,
                                  MultipartFile aadhaar,
                                  MultipartFile pan,
                                  MultipartFile resume,
                                  MultipartFile photo) throws IOException {

        Register user = registerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDocument doc = new UserDocument();
        doc.setUser(user);
        if (tenth != null) doc.setTenthMarksheet(tenth.getBytes());
        if (twelfth != null) doc.setTwelfthMarksheet(twelfth.getBytes());
        if (aadhaar != null) doc.setAadhaar(aadhaar.getBytes());
        if (pan != null) doc.setPan(pan.getBytes());
        if (resume != null) doc.setResume(resume.getBytes());
        if (photo != null) doc.setPhoto(photo.getBytes());

        documentRepository.save(doc);
        return "Documents uploaded successfully!";
    }
}
