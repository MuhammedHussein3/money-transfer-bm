package com.bm.transfer.transaction.entity;

import com.bm.transfer.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transactions")
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "from_id", nullable = false)
    private Long fromId;

    @Column(name = "to_account_number", nullable = false)
    private String toAccountNumber;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private HttpStatus status;

    @CreationTimestamp
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

}
