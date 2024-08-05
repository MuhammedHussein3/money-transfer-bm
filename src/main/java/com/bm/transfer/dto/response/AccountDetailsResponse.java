package com.bm.transfer.dto.response;

import lombok.Builder;

/*
data class AccountDetailsResponse(
    val accountNumber: String,
    val userName: String,
    val email: String,
    val country: String,
    val branch: String,
    val dateOfBirth: String,
)
 */
@Builder
public record AccountDetailsResponse(

        String accountNumber,
        String userName,
        String email,
        String country,
        String branch,
        String dateOfBirth
) {
}
