package com.bm.transfer.controller;

import com.bm.transfer.dto.response.TransactionPageResponse;
import com.bm.transfer.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService service;

    @InjectMocks
    private TransactionController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetTransactionsHistory() throws Exception {

        TransactionPageResponse response =
                TransactionPageResponse.builder()
                                .pageNumber(0)
                                        .pageSize(5)
                                                .build();
        when(service.getTransactionsHistory(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/transactions/transaction")
                        .param("pageNo", "0")
                        .param("pageSize", "5")
                        .param("sortBy", "id")
                        .param("account-number", "123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.someField").value("someValue")); // Adjust based on actual fields in TransactionPageResponse
    }
}
