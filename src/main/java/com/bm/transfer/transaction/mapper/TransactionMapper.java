package com.bm.transfer.transaction.mapper;

import com.bm.transfer.transaction.dto.request.TransactionRequestDto;
import com.bm.transfer.transaction.dto.response.TransactionResponseDto;
import com.bm.transfer.transaction.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {
    public Transaction mapToTransaction(TransactionRequestDto requestDto) {
        return Transaction.builder()
                .user(requestDto.userAccount())
                .toAccountNumber(requestDto.toAccountNumber())
                .amount(requestDto.amount())
                .status(requestDto.status())
                .build();

    }

    public TransactionResponseDto mapToTransactionResponseDto(Transaction transaction) {

        return TransactionResponseDto.builder()
                .transactionDate(transaction.getTransactionDate())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .build();
    }
}
