package com.bm.transfer.Favorite.service;

import com.bm.transfer.Favorite.dto.request.FavoriteCreateRequest;
import com.bm.transfer.Favorite.dto.response.FavoriteGetResponse;

import java.util.List;

public interface FavoriteService {


    void AddFavoriteRecipient(FavoriteCreateRequest request);

    List<FavoriteGetResponse> getFavoriteRecipients(String accountNumber);

    void deleteFavoriteRecipient(String accountNumber, String recipientAccountNumber);
}
