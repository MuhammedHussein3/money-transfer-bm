package com.bm.transfer.controller;

import com.bm.transfer.dto.request.AccountUpdateRequest;
import com.bm.transfer.dto.request.TransferRequest;
import com.bm.transfer.dto.response.AccountDetailsResponse;
import com.bm.transfer.service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
public class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserAccountService service;

    @InjectMocks
    private UserAccountController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testTransfer() throws Exception {
        TransferRequest request =
                TransferRequest.builder()
                        .fromId(1L)
                        .toAccountNumber("234-422451")
                        .amount(BigDecimal.valueOf(5234))
                        .build();


        when(service.transfer(any(TransferRequest.class))).thenReturn("Transfer successful");

        mockMvc.perform(post("/api/v1/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer successful"));
    }

    @Test
    void testUpdateAccount() throws Exception {
        AccountUpdateRequest request = AccountUpdateRequest
                .builder()
                .currentPassword("123456")
                .newPassword("dklfasjflieui23")
                .build();

        // Convert request object to JSON
        String requestJson = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(put("/api/v1/accounts")
                        .param("account-number", "123456") // Ensure this matches your controller's expected parameter
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Account with AccountNumber 123456 updated successfully."));
    }

    @Test
    void testCurrentBalance() throws Exception {
        when(service.currentBalance(anyString())).thenReturn(BigDecimal.valueOf(1000));

        mockMvc.perform(get("/api/v1/accounts/current-balance")
                        .param("account-number", "123456"))
                .andExpect(status().isOk())
                .andExpect(content().json("1000"));
    }

    @Test
    void testGetAccountDetails() throws Exception {
        AccountDetailsResponse response =
                AccountDetailsResponse.builder()
                        .accountNumber("123456")
                                .email("")
                                        .build();
        // Set up response fields as needed

        when(service.getAccountDetails(anyString())).thenReturn(response);

        mockMvc.perform(get("/api/v1/accounts/details")
                        .param("account-number", "123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("123456")); // Adjust based on actual response fields
    }
}
