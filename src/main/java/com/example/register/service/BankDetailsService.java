package com.example.register.service;

import com.example.register.entity.BankDetails;
import com.example.register.entity.Register;
import com.example.register.repository.BankDetailsRepository;
import com.example.register.repository.RegisterRepository;
import org.springframework.stereotype.Service;

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
}
