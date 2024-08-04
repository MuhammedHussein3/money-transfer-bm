package com.bm.transfer.account.service;


import com.bm.transfer.account.dto.request.AccountCreateRequest;
import com.bm.transfer.account.dto.request.AccountUpdateRequest;
import com.bm.transfer.account.dto.request.TransferRequest;
import com.bm.transfer.account.dto.response.AccountResponseDto;
import com.bm.transfer.account.dto.send.RecipientAccount;
import jakarta.mail.MessagingException;

import java.math.BigDecimal;

public interface AccountService {

//    AccountResponseDto createAccount(Long userId, AccountCreateRequest request);

    String transfer(TransferRequest request) throws MessagingException;

    BigDecimal currentBalance(String accountNumber);

    void updateAccount(Long accountId, AccountUpdateRequest request);

}
