package com.bm.transfer.transaction.repository;

import com.bm.transfer.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query("""
            SELECT t
            FROM Transaction t
            WHERE t.user.accountNumber = :accountNumber
            """)
    Page<Transaction> getUserTransactionsHistoryByAccountNumber(String accountNumber, Pageable pageable);
}
