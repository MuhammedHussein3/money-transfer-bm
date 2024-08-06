package com.bm.transfer.service.serviceImpl;

import com.bm.transfer.dto.request.TransactionRequestDto;
import com.bm.transfer.dto.response.TransactionPageResponse;
import com.bm.transfer.entity.Transaction;
import com.bm.transfer.mapper.TransactionMapper;
import com.bm.transfer.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {


    @Mock
    private TransactionRepository repository;

    @Mock
    private TransactionMapper mapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransactionRequestDto requestDto;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        requestDto = TransactionRequestDto.builder()
                .userAccount(null)
                .toAccountNumber("123-456")
                .amount(null)
                .status(null)
                .build();

        transaction = Transaction.builder()
                .id(1L)
                .build();
    }

    @Test
    void shouldCreateTransaction() {
        when(mapper.mapToTransaction(any(TransactionRequestDto.class))).thenReturn(transaction);
        when(repository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.createTransaction(requestDto);

        verify(mapper, times(1)).mapToTransaction(requestDto);
        verify(repository, times(1)).save(transaction);
    }

    @Test
    void shouldGetTransactionsHistory() {
        String accountNumber = "123-456";
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "date";

        Transaction transaction = Transaction.builder().id(1L).build();
        Page<Transaction> transactionPage = Page.empty(PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy)));

        when(repository.getUserTransactionsHistoryByAccountNumber(anyString(), any(Pageable.class))).thenReturn(transactionPage);
        when(mapper.mapToTransactionResponseDto(any(Transaction.class))).thenReturn(null); // Mock the mapping

        TransactionPageResponse response = transactionService.getTransactionsHistory(pageNo, pageSize, sortBy, accountNumber);

        assertEquals(0, response.totalElement());
        assertEquals(0, response.totalPages());
        assertEquals(pageNo, response.pageNumber());
        assertEquals(pageSize, response.pageSize());
        assertEquals(List.of(), response.transactionsHistoryForThisAccount());
    }
}
