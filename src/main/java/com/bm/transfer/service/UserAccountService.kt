package com.bm.transfer.service;


import com.bm.transfer.dto.request.AccountUpdateRequest;
import com.bm.transfer.dto.request.TransferRequest;
import jakarta.mail.MessagingException;

import java.math.BigDecimal;

public interface UserAccountService {

//    AccountResponseDto createAccount(Long userId, AccountCreateRequest request);

    String transfer(TransferRequest request) throws MessagingException;

    BigDecimal currentBalance(String accountNumber);

    void updateAccount(String accountNumber, AccountUpdateRequest request);

}
