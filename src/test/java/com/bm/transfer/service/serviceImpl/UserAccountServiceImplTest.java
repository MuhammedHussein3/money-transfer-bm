package com.bm.transfer.service.serviceImpl;

import com.bm.transfer.dto.request.AccountUpdateRequest;
import com.bm.transfer.dto.request.TransactionRequestDto;
import com.bm.transfer.dto.request.TransferRequest;
import com.bm.transfer.dto.response.AccountDetailsResponse;
import com.bm.transfer.email.SendEmailService;
import com.bm.transfer.entity.User;
import com.bm.transfer.exceptions.AccountNotFoundException;
import com.bm.transfer.exceptions.InsufficientBalanceException;
import com.bm.transfer.exceptions.PasswordException;
import com.bm.transfer.mapper.UserMapper;
import com.bm.transfer.repository.UserRepository;
import com.bm.transfer.service.TransactionService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserAccountServiceImplTest {

    @Mock
    private SendEmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TransactionService transactionService;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserAccountServicedImpl userAccountService;

    private User userFrom;
    private User userTo;

    @BeforeEach
    void setUp() {
        userFrom = User.builder()
                .id(1L)
                .userName("userFrom")
                .email("from@example.com")
                .balance(BigDecimal.valueOf(10000))
                .password("hashedPassword")
                .build();

        userTo = User.builder()
                .id(2L)
                .userName("userTo")
                .email("to@example.com")
                .balance(BigDecimal.valueOf(5000))
                .password("hashedPassword")
                .build();
    }



    @Test
    void shouldThrowExceptionWhenAccountNotFound() {
        TransferRequest request = new TransferRequest(1L, "123-456", BigDecimal.valueOf(1000));

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> userAccountService.transfer(request));
    }

    @Test
    void shouldThrowExceptionForInsufficientBalance() {
        TransferRequest request = new TransferRequest(1L, "123-456", BigDecimal.valueOf(20000));

        when(repository.findById(anyLong())).thenReturn(Optional.of(userFrom));
        when(repository.getUserByAccountNumber(anyString())).thenReturn(Optional.of(userTo));

        assertThrows(InsufficientBalanceException.class, () -> userAccountService.transfer(request));
    }

    @Test
    void shouldReturnCurrentBalance() {
        when(repository.getCurrentBalance(anyString())).thenReturn(Optional.of(BigDecimal.valueOf(1000)));

        BigDecimal balance = userAccountService.currentBalance("123-456");

        assertEquals(BigDecimal.valueOf(1000), balance);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFoundForCurrentBalance() {
        when(repository.getCurrentBalance(anyString())).thenReturn(Optional.empty());

        assertThrows(InsufficientBalanceException.class, () -> userAccountService.currentBalance("123-456"));
    }

    @Test
    void shouldUpdateAccountPassword() {
        AccountUpdateRequest request = new AccountUpdateRequest("oldPassword", "newPassword");

        when(repository.getUserByAccountNumber(anyString())).thenReturn(Optional.of(userFrom));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("newHashedPassword");

        userAccountService.updateAccount("123-456", request);

        verify(repository, times(1)).save(userFrom);
    }

    @Test
    void shouldThrowExceptionForInvalidPassword() {
        AccountUpdateRequest request = new AccountUpdateRequest("wrongOldPassword", "newPassword");

        when(repository.getUserByAccountNumber(anyString())).thenReturn(Optional.of(userFrom));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(PasswordException.class, () -> userAccountService.updateAccount("123-456", request));
    }

    @Test
    void shouldGenerateAccountNumber() {
        String accountNumber = userAccountService.generateAccountNumber("USD");

        assertNotNull(accountNumber);
        assertTrue(accountNumber.startsWith("20"));
        assertTrue(accountNumber.length() == 10);
    }


    @Test
    void shouldThrowExceptionWhenAccountDetailsNotFound() {
        when(repository.getUserByAccountNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> userAccountService.getAccountDetails("123-456"));
    }
}
