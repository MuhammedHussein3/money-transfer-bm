package com.bm.transfer.transaction.dto.request;

import com.bm.transfer.account.entity.Account;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Builder

public record TransactionRequestDto(

        Account account,

        Long fromId,

        String toAccountNumber,

        String recipient,

        BigDecimal amount,

        HttpStatus status
) {
}
