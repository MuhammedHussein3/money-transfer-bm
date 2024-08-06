package com.bm.transfer.repository;

import com.bm.transfer.entity.Transaction;
import com.bm.transfer.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionRepositoryTest {
    @Mock
    private TransactionRepository transactionRepository;

    private User user;
    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .userName("muhammad_Hussein")
                .password("13?23fadFAd")
                .email("muhammadhussein2312@gmail.com")
                .accountNumber("981-981345")
                .balance(BigDecimal.valueOf(10000.00))
                .build();

        transaction1 = Transaction.builder()
                .id(1L)
                .user(user)
                .toAccountNumber("123-455216")
                .amount(BigDecimal.valueOf(100))
                .status(HttpStatus.CONFLICT)
                .transactionDate(LocalDateTime.now())
                .build();

        transaction2 = Transaction.builder()
                .id(2L)
                .user(user)
                .toAccountNumber("789-012")
                .amount(BigDecimal.valueOf(5000))
                .status(HttpStatus.CREATED)
                .transactionDate(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldGetUserTransactionsHistoryByAccountNumber() {

        Sort sort = Sort.by("transactionDate");
        Pageable pageable = PageRequest.of(0, 5, sort);
        Page<Transaction> transactionsPage = new PageImpl<>(List.of(transaction1, transaction2), pageable, 2);

        when(transactionRepository.getUserTransactionsHistoryByAccountNumber(anyString(), any(Pageable.class)))
                .thenReturn(transactionsPage);

        Page<Transaction> result = transactionRepository.getUserTransactionsHistoryByAccountNumber("981-981345", pageable);

        assertTrue(result.hasContent());
        assertEquals(2, result.getTotalElements());
        assertEquals(transaction1, result.getContent().get(0));
        assertEquals(transaction2, result.getContent().get(1));
    }
}
