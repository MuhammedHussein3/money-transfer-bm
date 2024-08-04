package com.bm.transfer.account.controller;

import com.bm.transfer.account.dto.request.AccountCreateRequest;
import com.bm.transfer.account.dto.request.AccountUpdateRequest;
import com.bm.transfer.account.dto.request.TransferRequest;
import com.bm.transfer.account.dto.response.AccountResponseDto;

import com.bm.transfer.account.service.AccountService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private final AccountService service;

//    @PostMapping
//    public ResponseEntity<AccountResponseDto> createAccount(
//           @NonNull @RequestParam("user-id") Long userId,
//          @Valid @RequestBody AccountCreateRequest request
//            ){
//        return ResponseEntity.status(
//                HttpStatus.CREATED
//        ).body(service.createAccount(userId, request));
//    }


    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @Valid @RequestBody TransferRequest request
    ) throws MessagingException {
        return ResponseEntity.ok(service.transfer(request));
    }

    @PutMapping
    public ResponseEntity<?> updateAccount(
            @NotNull(message = "Account ID is required")
            @Min(value = 1, message = "Account ID must be a positive number")
            @RequestParam("account-id") Long accountId,
            @Valid @RequestBody AccountUpdateRequest request
    ) {
        service.updateAccount(accountId, request);
        return ResponseEntity.ok(
                String.format("Account with ID %d updated successfully.", accountId));
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
