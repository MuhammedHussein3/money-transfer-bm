package com.bm.transfer.controller

import com.bm.transfer.dto.request.AccountUpdateRequest
import com.bm.transfer.dto.request.TransferRequest
import com.bm.transfer.service.UserAccountService
import jakarta.mail.MessagingException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
@CrossOrigin
open class UserAccountController(
    private val service: UserAccountService
) {

    @PostMapping("/transfer")
    fun transfer(
        @Valid @RequestBody request: TransferRequest
    ): ResponseEntity<String> {
        return ResponseEntity.ok(service.transfer(request))
    }

    @PutMapping
    fun updateAccount(
        @RequestParam("account-number") accountNumber: String,
        @Valid @RequestBody request: AccountUpdateRequest
    ): ResponseEntity<String> {
        service.updateAccount(accountNumber, request)
        return ResponseEntity.ok("Account with AccountNumber $accountNumber updated successfully.")
    }

    @GetMapping("/current-balance")
    fun currentBalance(
        @RequestParam("account-number") accountNumber: String
    ): ResponseEntity<BigDecimal> {
        return ResponseEntity.ok(service.currentBalance(accountNumber))
    }
}
