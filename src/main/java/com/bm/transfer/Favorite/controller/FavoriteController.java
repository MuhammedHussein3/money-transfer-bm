package com.bm.transfer.Favorite.controller;

import com.bm.transfer.Favorite.dto.request.FavoriteCreateRequest;
import com.bm.transfer.Favorite.dto.response.FavoriteGetResponse;
import com.bm.transfer.Favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
@CrossOrigin(

)
public class FavoriteController{

    private final FavoriteService service;

    @PostMapping
    public ResponseEntity<String> AddFavoriteRecipient(
            @RequestBody FavoriteCreateRequest request
    ) {

        service.AddFavoriteRecipient(request);
        return ResponseEntity
                   .status(HttpStatus.CREATED)
                        .body(
                           String.format("Favorite recipient %s added successfully", request.recipientName())
                        );
    }


    @GetMapping
    public ResponseEntity<List<FavoriteGetResponse>> getFavoriteRecipients(
            @RequestParam("account-number") String accountNumber
    ) {
        return ResponseEntity.ok(service.getFavoriteRecipients(accountNumber));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFavorite(
            @RequestParam("account-number") String accountNumber,
            @RequestParam("recipient-account-number") String recipientAccountNumber
    ){
        service.deleteFavoriteRecipient(accountNumber, recipientAccountNumber);
        return ResponseEntity.ok(
                String.format("Favorite recipient %s deleted successfully", recipientAccountNumber)
        );
    }
}
