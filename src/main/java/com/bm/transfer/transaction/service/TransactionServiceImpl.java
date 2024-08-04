package com.bm.transfer.transaction.service;

import com.bm.transfer.transaction.dto.TransactionPageResponse;
import com.bm.transfer.transaction.dto.request.TransactionRequestDto;
import com.bm.transfer.transaction.entity.Transaction;
import com.bm.transfer.transaction.mapper.TransactionMapper;
import com.bm.transfer.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository repository;
    private final TransactionMapper mapper;



    @Caching(evict = {
            @CacheEvict(value = "Transaction.getTransaction", allEntries = true) // Evict all entries for this cache
    })
    @Override
    public void createTransaction(TransactionRequestDto requestDto) {

        var transaction = mapper.mapToTransaction(requestDto);
        System.out.println(transaction.toString());
        repository.save(transaction);
    }


    @Cacheable(value = "Transaction.getTransaction", key = "#accountNumber")
    public TransactionPageResponse getTransactionsHistory(int pageNo, int pageSize, String sortBy, String accountNumber){
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Transaction> transactionPage =  repository.getUserTransactionsHistoryByAccountId(accountNumber, pageable);


        return TransactionPageResponse.builder()
                .totalElement(transactionPage.getNumberOfElements())
                .totalPages(transactionPage.getTotalPages())
                .pageNumber(pageNo)
                .pageSize(pageSize)
                .transactionsHistoryForThisAccount(
                        transactionPage.getContent().
                                stream()
                                .map(mapper::mapToTransactionResponseDto).toList()
                )
                .build();
    }
}
