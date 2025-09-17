package com.example.register.controller;

import com.example.register.entity.BankDetails;
import com.example.register.service.BankDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
public class BankDetailsController {

    private final BankDetailsService bankDetailsService;

    public BankDetailsController(BankDetailsService bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    @PostMapping("/add/{userId}")
    public String addBankDetails(@PathVariable Long userId, @RequestBody BankDetails bankDetails) {
        return bankDetailsService.addBankDetails(userId, bankDetails);
    }
    // 🔹 Update existing bank details
    @PutMapping("/edit/{userId}")
    public String editBankDetails(@PathVariable Long userId, @RequestBody BankDetails bankDetails) {
        return bankDetailsService.editBankDetails(userId, bankDetails);
    }
}
