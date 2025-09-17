package com.example.register.service;

import com.example.register.entity.BankDetails;
import com.example.register.entity.Register;
import com.example.register.repository.BankDetailsRepository;
import com.example.register.repository.RegisterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankDetailsService {

    private final BankDetailsRepository bankDetailsRepository;
    private final RegisterRepository registerRepository;

    public BankDetailsService(BankDetailsRepository bankDetailsRepository, RegisterRepository registerRepository) {
        this.bankDetailsRepository = bankDetailsRepository;
        this.registerRepository = registerRepository;
    }

    public String addBankDetails(Long userId, BankDetails bankDetails) {
        Register user = registerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        bankDetails.setUser(user);
        bankDetailsRepository.save(bankDetails);

        return "Bank details added successfully!";
    }
    // 🔹 Edit Bank Details
    public String editBankDetails(Long userId, BankDetails updatedDetails) {
        Optional<BankDetails> existingOpt = bankDetailsRepository.findByUserId(userId);

        if (existingOpt.isEmpty()) {
            return "Bank details not found for this user!";
        }

        BankDetails existing = existingOpt.get();
        existing.setAccountHolderName(updatedDetails.getAccountHolderName());
        existing.setAccountNumber(updatedDetails.getAccountNumber());
        existing.setBankName(updatedDetails.getBankName());
        existing.setIfscCode(updatedDetails.getIfscCode());
        existing.setBranchName(updatedDetails.getBranchName());

        bankDetailsRepository.save(existing);
        return "Bank details updated successfully!";
    }
}
