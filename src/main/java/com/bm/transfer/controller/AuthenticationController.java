package com.bm.transfer.controller;

import com.bm.transfer.dto.request.AuthenticationRequest;
import com.bm.transfer.dto.response.AuthenticationResponse;
import com.bm.transfer.service.serviceImpl.AuthenticationService;
import com.bm.transfer.dto.request.RegistrationRequest;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")

public class AuthenticationController {

    private final AuthenticationService service;

    @CrossOrigin(origins = "*")
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user and returns an authentication response with a JWT token",
            responses = {
                    @ApiResponse(responseCode = "202", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegistrationRequest request) {
        return ResponseEntity.accepted().body(service.register(request));
    }

    @CrossOrigin(origins = "*")
    @Operation(
            summary = "Authenticate a user",
            description = "Authenticates a user and returns an authentication response with a JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @CrossOrigin(origins = "*")
    @Operation(
            summary = "Logout a user",
            description = "Logs out a user by invalidating the JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged out successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        service.logout(token);
        return ResponseEntity.ok("Logged out successfully.");
    }
}