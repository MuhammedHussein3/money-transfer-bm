package com.bm.transfer.account.service;


import com.bm.transfer.account.dto.request.AccountUpdateRequest;
import com.bm.transfer.account.dto.request.TransferRequest;
import jakarta.mail.MessagingException;

import java.math.BigDecimal;

public interface UserAccountService {

//    AccountResponseDto createAccount(Long userId, AccountCreateRequest request);

    String transfer(TransferRequest request) throws MessagingException;

    BigDecimal currentBalance(String accountNumber);

    void updateAccount(String accountNumber, AccountUpdateRequest request);

}
