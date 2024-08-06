package com.bm.transfer.service.serviceImpl;

import com.bm.transfer.dto.request.FavoriteCreateRequest;
import com.bm.transfer.dto.response.FavoriteGetResponse;
import com.bm.transfer.entity.Favorite;
import com.bm.transfer.entity.User;
import com.bm.transfer.exceptions.AccountNotFoundException;
import com.bm.transfer.exceptions.FavoriteNotFoundException;
import com.bm.transfer.mapper.FavoriteMapper;
import com.bm.transfer.repository.FavoriteRepository;
import com.bm.transfer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceImplTest {

    @Mock
    private FavoriteRepository repository;

    @Mock
    private FavoriteMapper mapper;

    @Mock
    private UserRepository accountRepository;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    private User user;
    private Favorite favorite;
    private FavoriteCreateRequest createRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .userName("testuser")
                .accountNumber("123-456")
                .build();

        favorite = Favorite.builder()
                .id(1L)
                .user(user)
                .recipientAccountNumber("654-321")
                .recipientName("recipient")
                .build();

        createRequest = FavoriteCreateRequest
                .builder()
                .accountNumber("123-456")
                .recipientAccountNumber("654-321")
                .recipientName("recipient")
                .build();
    }

    @Test
    void shouldAddFavoriteRecipient() {
        when(accountRepository.getUserByAccountNumber(anyString())).thenReturn(Optional.of(user));
        when(accountRepository.getUserByAccountNumberAndUserName(anyString(), anyString())).thenReturn(Optional.of(user));
        when(mapper.mapToFavorite(any(FavoriteCreateRequest.class))).thenReturn(favorite);

        favoriteService.AddFavoriteRecipient(createRequest);

        Mockito.verify(repository, times(1)).save(favorite);
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenAddingFavorite() {
        when(accountRepository.getUserByAccountNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> favoriteService.AddFavoriteRecipient(createRequest));
    }

    @Test
    void shouldGetFavoriteRecipients() {
        when(accountRepository.getUserByAccountNumber(anyString())).thenReturn(Optional.of(user));
        when(repository.getFavoriteRecipients(anyString())).thenReturn(List.of(favorite));
        when(mapper.mapToFavoriteGetResponse(any(Favorite.class))).thenReturn(new FavoriteGetResponse("recipient"));

        List<FavoriteGetResponse> result = favoriteService.getFavoriteRecipients("123-456");

        assertEquals(1, result.size());
        Mockito.verify(repository, times(1)).getFavoriteRecipients("123-456");
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenGettingFavorites() {
        when(accountRepository.getUserByAccountNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> favoriteService.getFavoriteRecipients("123-456"));
    }

    @Test
    void shouldDeleteFavoriteRecipient() {
        when(accountRepository.getUserByAccountNumber(anyString())).thenReturn(Optional.of(user));
        when(repository.deleteFavoritesByUserAccountNumberAndRecipientAccountNumber(anyString(), anyString())).thenReturn(1);

        favoriteService.deleteFavoriteRecipient("123-456", "654-321");

        Mockito.verify(repository, times(1)).deleteFavoritesByUserAccountNumberAndRecipientAccountNumber("123-456", "654-321");
    }

    @Test
    void shouldThrowFavoriteNotFoundExceptionWhenDeletingFavorite() {
        when(accountRepository.getUserByAccountNumber(anyString())).thenReturn(Optional.of(user));
        when(accountRepository.getUserByAccountNumber(anyString())).thenReturn(Optional.of(user));
        when(repository.deleteFavoritesByUserAccountNumberAndRecipientAccountNumber(anyString(), anyString())).thenReturn(0);

        assertThrows(FavoriteNotFoundException.class, () -> favoriteService.deleteFavoriteRecipient("123-456", "654-321"));
    }
}
