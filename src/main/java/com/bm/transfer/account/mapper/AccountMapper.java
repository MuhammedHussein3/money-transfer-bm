package com.bm.transfer.account.mapper;

import com.bm.transfer.account.dto.request.AccountCreateRequest;
import com.bm.transfer.account.dto.response.AccountResponseDto;
import com.bm.transfer.account.entity.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountMapper {


    public Account mapToAccount(AccountCreateRequest request, String accountNumber) {

        return Account.builder()
                .email(request.email())
                .userName(request.userName())
                .country(request.country())
                .accountNumber(accountNumber)
                .dateOfBirth(request.dateOfBirth())
                .password(request.password())
                .balance(BigDecimal.valueOf(10000.00234))
                .build();

    }

    public AccountResponseDto mapToAccountResponseDto(Account account) {
        return AccountResponseDto.builder()
                .country(account.getCountry())
                .accountNumber(account.getAccountNumber())
                .id(account.getId())
                .balance(account.getBalance())
                .userName(account.getUserName())
                .email(account.getEmail())
                .dateOfBirth(account.getDateOfBirth())
                .createdAt(account.getCreatedAt())
                .build();
    }
}
