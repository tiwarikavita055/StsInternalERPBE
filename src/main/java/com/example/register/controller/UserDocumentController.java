package com.example.register.controller;

import com.example.register.service.UserDocumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class UserDocumentController {

    private final UserDocumentService documentService;

    public UserDocumentController(UserDocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload/{userId}")
    public String uploadDocs(@PathVariable Long userId,
                             @RequestParam(value = "tenth", required = false) MultipartFile tenth,
                             @RequestParam(value = "twelfth", required = false) MultipartFile twelfth,
                             @RequestParam(value = "aadhaar", required = false) MultipartFile aadhaar,
                             @RequestParam(value = "pan", required = false) MultipartFile pan,
                             @RequestParam(value = "resume", required = false) MultipartFile resume,
                             @RequestParam(value = "photo", required = false) MultipartFile photo) throws Exception {
        return documentService.uploadDocuments(userId, tenth, twelfth, aadhaar, pan, resume, photo);
    }
}
