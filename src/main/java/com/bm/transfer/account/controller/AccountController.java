package com.bm.transfer.account.controller;

import com.bm.transfer.account.dto.request.AccountUpdateRequest;
import com.bm.transfer.account.dto.request.TransferRequest;

import com.bm.transfer.account.service.UserAccountService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
@CrossOrigin(

)
public class AccountController {

    private final UserAccountService service;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @Valid @RequestBody TransferRequest request
    ) throws MessagingException {
        return ResponseEntity.ok(service.transfer(request));
    }

    @PutMapping
    public ResponseEntity<?> updateAccount(
            @NotNull(message = "Account ID is required")
            @RequestParam("account-number") String accountNumber,
            @Valid @RequestBody AccountUpdateRequest request
    ) {
        service.updateAccount(accountNumber, request);
        return ResponseEntity.ok(
                String.format("Account with AccountNumber %s updated successfully.", accountNumber));
    }


    @GetMapping("/current-balance")
    public ResponseEntity<BigDecimal> currentBalance(
            @RequestParam("account-number") String accountNumber
    ) {

        return ResponseEntity.ok(
                service.currentBalance(accountNumber)
        );
    }
}
