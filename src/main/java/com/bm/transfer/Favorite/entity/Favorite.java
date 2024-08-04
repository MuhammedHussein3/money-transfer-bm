package com.bm.transfer.Favorite.entity;

import com.bm.transfer.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_account_number", nullable = false)
    private String recipientAccountNumber;
}
