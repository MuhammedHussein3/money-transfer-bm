package com.bm.transfer.transaction.dto.request;

import com.bm.transfer.account.entity.Account;
import com.bm.transfer.authentication.user.User;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Builder

public record TransactionRequestDto(

        User userAccount,


        String toAccountNumber,


        BigDecimal amount,

        HttpStatus status
) {
}
