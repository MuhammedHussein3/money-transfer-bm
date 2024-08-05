package com.bm.transfer.controller;

import com.bm.transfer.dto.request.AccountUpdateRequest;
import com.bm.transfer.dto.request.TransferRequest;
import com.bm.transfer.dto.response.AccountDetailsResponse;
import com.bm.transfer.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
@Tag(name = "User Accounts", description = "Endpoints for managing user accounts")
public class UserAccountController {

    private final UserAccountService service;

    @Operation(
            summary = "Transfer funds",
            description = "Transfers funds from one account to another",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transfer successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid transfer request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @Valid @RequestBody TransferRequest request
    ) throws MessagingException {
        return ResponseEntity.ok(service.transfer(request));
    }

    @Operation(
            summary = "Update account",
            description = "Updates the details of an account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid account update request"),
                    @ApiResponse(responseCode = "404", description = "Account not found")
            }
    )
    @PutMapping
    public ResponseEntity<String> updateAccount(
            @RequestParam("account-number") String accountNumber,
            @Valid @RequestBody AccountUpdateRequest request
    ) {
        service.updateAccount(accountNumber, request);
        return ResponseEntity.ok("Account with AccountNumber " + accountNumber + " updated successfully.");
    }

    @Operation(
            summary = "Get current balance",
            description = "Retrieves the current balance of an account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Current balance retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Account not found")
            }
    )
    @GetMapping("/current-balance")
    public ResponseEntity<BigDecimal> currentBalance(
            @RequestParam("account-number") String accountNumber
    ) {
        return ResponseEntity.ok(service.currentBalance(accountNumber));
    }

    @Operation(
            summary = "Get account details",
            description = "Retrieves the details of an account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account details retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Account not found")
            }
    )
    @GetMapping("/details")
    public ResponseEntity<AccountDetailsResponse> getAccountDetails(
            @RequestParam("account-number") String accountNumber
    ) {
        return ResponseEntity.ok(service.getAccountDetails(accountNumber));
    }
}

