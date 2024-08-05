package com.bm.transfer.transaction.service;

import com.bm.transfer.transaction.dto.TransactionPageResponse;
import com.bm.transfer.transaction.dto.request.TransactionRequestDto;

public interface TransactionService {
    void createTransaction(TransactionRequestDto requestDto);

    TransactionPageResponse getTransactionsHistory(int pageNo, int pageSize, String sortBy, String accountNumber);
}
