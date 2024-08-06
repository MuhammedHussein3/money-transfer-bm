package com.bm.transfer.controller

import com.bm.transfer.dto.response.TransactionPageResponse
import com.bm.transfer.service.TransactionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transactions", description = "Endpoints for managing transaction history")
class TransactionController(
    private val service: TransactionService
) {

    @CrossOrigin(origins = ["*"])
    @Operation(
        summary = "Get transaction history",
        description = "Retrieves the transaction history for a specific account",
        responses = [
            ApiResponse(responseCode = "200", description = "Transaction history retrieved successfully"),
            ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            ApiResponse(responseCode = "404", description = "Account not found")
        ]
    )
    @GetMapping("/transaction")
    fun getTransactionsHistory(
        @RequestParam(defaultValue = "0") pageNo: Int,
        @RequestParam(defaultValue = "5") pageSize: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam("account-number") accountNumber: String
    ): TransactionPageResponse {
        return service.getTransactionsHistory(pageNo, pageSize, sortBy, accountNumber)
    }
}