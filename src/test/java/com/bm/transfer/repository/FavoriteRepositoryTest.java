package com.bm.transfer.repository;

import com.bm.transfer.dto.enums.Branch;
import com.bm.transfer.dto.enums.Country;
import com.bm.transfer.entity.Favorite;
import com.bm.transfer.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FavoriteRepositoryTest {
    @Mock
    private FavoriteRepository favoriteRepository;

    private User user;
    private Favorite favorite;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .userName("testuser")
                .password("password")
                .email("testuser@example.com")
                .accountNumber("123-233789")
                .balance(BigDecimal.valueOf(1000))
                .branch(Branch.NORTH)
                .country(Country.EGP)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .enabled(true)
                .accountLocked(false)
                .transactions(Collections.emptyList())
                .roles(Collections.emptyList())
                .favorites(Collections.emptyList())
                .build();

        favorite = Favorite.builder()
                .id(1L)
                .user(user)
                .recipientAccountNumber("123-233789")
                .recipientName("recipient")
                .build();
    }

    @Test
    void shouldGetFavoriteRecipients() {
        when(favoriteRepository.getFavoriteRecipients(anyString())).thenReturn(List.of(favorite));

        List<Favorite> result = favoriteRepository.getFavoriteRecipients("123-233789");

        assertTrue(result.size() > 0);
        assertEquals(1, result.size());
        assertEquals("123-233789", result.get(0).getRecipientAccountNumber());
    }

    @Test
    void shouldDeleteFavoritesByUserAccountNumberAndRecipientAccountNumber() {
        when(favoriteRepository.deleteFavoritesByUserAccountNumberAndRecipientAccountNumber(anyString(), anyString())).thenReturn(1);

        int result = favoriteRepository.deleteFavoritesByUserAccountNumberAndRecipientAccountNumber("123-233789", "recipient");

        assertEquals(1, result);
        verify(favoriteRepository).deleteFavoritesByUserAccountNumberAndRecipientAccountNumber("123-233789", "recipient");
    }
}
