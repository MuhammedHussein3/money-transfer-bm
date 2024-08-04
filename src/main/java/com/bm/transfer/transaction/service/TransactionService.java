package com.bm.transfer.transaction.service;

import com.bm.transfer.transaction.dto.TransactionPageResponse;
import com.bm.transfer.transaction.dto.request.TransactionRequestDto;
import com.bm.transfer.transaction.dto.request.TransactionRequestHistory;
import com.bm.transfer.transaction.dto.response.TransactionResponseDto;
import com.bm.transfer.transaction.entity.Transaction;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    void createTransaction(TransactionRequestDto requestDto);

    @Cacheable(value = "Transaction.getTransaction", key = "#accountNumber")
    TransactionPageResponse getTransactionsHistory(int pageNo, int pageSize, String sortBy, String accountNumber);
}
