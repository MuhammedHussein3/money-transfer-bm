package com.bm.transfer.authentication.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {

    private final String accountNumber;
    private final String token;
}
