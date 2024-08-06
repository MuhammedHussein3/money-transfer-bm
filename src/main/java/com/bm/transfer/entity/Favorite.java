package com.bm.transfer.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "favorites")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Favorite.detail",
                attributeNodes = {
                        @NamedAttributeNode("user")
                }
        )
})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private User user;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_account_number", nullable = false)
    private String recipientAccountNumber;
}
