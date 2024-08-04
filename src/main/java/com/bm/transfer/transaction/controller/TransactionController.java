package com.bm.transfer.transaction.controller;

import com.bm.transfer.transaction.dto.TransactionPageResponse;
import com.bm.transfer.transaction.dto.request.TransactionRequestHistory;
import com.bm.transfer.transaction.dto.response.TransactionResponseDto;
import com.bm.transfer.transaction.entity.Transaction;
import com.bm.transfer.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@CrossOrigin(
        origins = {
                "http://localhost:4200"
        }
)
public class TransactionController {

    private final TransactionService service;


    @ResponseBody
    @GetMapping("/transaction")
    public TransactionPageResponse getTransactionsHistory(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam("account-number") String accountNumber
    ){
        return  service.getTransactionsHistory(pageNo, pageSize, sortBy, accountNumber);
    }
}
