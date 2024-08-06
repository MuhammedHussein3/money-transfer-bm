package com.bm.transfer.controller;

import com.bm.transfer.dto.request.FavoriteCreateRequest;
import com.bm.transfer.dto.response.FavoriteGetResponse;
import com.bm.transfer.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Favorites", description = "Endpoints for managing favorite recipients")
public class FavoriteController{

    private final FavoriteService service;

    @CrossOrigin(origins = "*")
    @Operation(
            summary = "Add a favorite recipient",
            description = "Adds a favorite recipient for a specific account",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Favorite recipient added successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
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

    @CrossOrigin(origins = "*")
    @Operation(
            summary = "Get favorite recipients",
            description = "Retrieves a list of favorite recipients for a specific account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Favorite recipients retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Account not found")
            }
    )
    @GetMapping
    public ResponseEntity<List<FavoriteGetResponse>> getFavoriteRecipients(
            @RequestParam("account-number") String accountNumber
    ) {
        return ResponseEntity.ok(service.getFavoriteRecipients(accountNumber));
    }


    @CrossOrigin(origins = "*")
    @Operation(
            summary = "Delete a favorite recipient",
            description = "Deletes a favorite recipient for a specific account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Favorite recipient deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Favorite recipient not found")
            }
    )
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
