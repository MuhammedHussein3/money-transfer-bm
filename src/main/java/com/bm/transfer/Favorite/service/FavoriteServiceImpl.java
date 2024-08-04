package com.bm.transfer.Favorite.service;

import com.bm.transfer.Favorite.dto.request.FavoriteCreateRequest;
import com.bm.transfer.Favorite.dto.response.FavoriteGetResponse;
import com.bm.transfer.Favorite.exceptions.FavoriteNotFoundException;
import com.bm.transfer.Favorite.mapper.FavoriteMapper;
import com.bm.transfer.Favorite.repository.FavoriteRepository;
import com.bm.transfer.account.exception.AccountNotFoundException;
import com.bm.transfer.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository repository;
    private final FavoriteMapper mapper;
    private final AccountRepository accountRepository;



    @Override
    public void AddFavoriteRecipient(FavoriteCreateRequest request) {

        // check account
        var account =  accountRepository.findById(request.accountId())
                        .orElseThrow( () -> new AccountNotFoundException(String.format("Account Not Found With ID:: %s", request.accountId())));
        // check favorite account
         accountRepository.getAccountByAccountNumberAndUserName(request.recipientAccountNumber(), request.recipientName())
                .orElseThrow( () -> new AccountNotFoundException(String.format("Account Not Found With AccountNumber:: %s And RecipientName:: %s", request.recipientAccountNumber(), request.recipientName())));


         repository.save(mapper.mapToFavorite(request));
    }
    @Cacheable(value = "Favorite.getFavoriteRecipients", key = "#raccountId")
    @Override
    public List<FavoriteGetResponse> getFavoriteRecipients(String accountNumber) {

        accountRepository.getAccountByAccountNumber(accountNumber).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account Not Found With ID:: %s", accountNumber))
        );

        return repository.getFavoriteRecipients(accountNumber)
                .stream()
                .map(mapper::mapToFavoriteGetResponse)
                .toList();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteFavoriteRecipient(String accountNumber, String recipientAccountNumber) {
        accountRepository.getAccountByAccountNumber(accountNumber).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account Not Found With AccountNumber:: %s", accountNumber))
        );

        accountRepository.getAccountByAccountNumber(recipientAccountNumber)
                .orElseThrow(
                        () -> new AccountNotFoundException(String.format("RecipientAccountNumber not found with recipientAccountNumber:: %s", recipientAccountNumber))
                );
        int a = repository.deleteFavoritesByAccountAccountNumberAndRecipientAccountNumber(accountNumber, recipientAccountNumber);

        if (a == 0){
            throw new FavoriteNotFoundException(
                    String.format("Not Found Favorite Recipient %s", recipientAccountNumber)
            );
        }
    }
}
