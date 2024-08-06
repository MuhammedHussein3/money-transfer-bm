package com.bm.transfer.controller;

import com.bm.transfer.authentication.security.JwtService;
import com.bm.transfer.dto.request.FavoriteCreateRequest;
import com.bm.transfer.dto.response.FavoriteGetResponse;
import com.bm.transfer.service.FavoriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class FavoriteControllerTest  {

    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Mock
    private FavoriteService service;

    @InjectMocks
    private FavoriteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void testAddFavoriteRecipient() throws Exception {
        FavoriteCreateRequest request = FavoriteCreateRequest.builder()
                .recipientName("John Doe")
                .accountNumber("123456")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("Favorite recipient John Doe added successfully"));
    }

    @Test
    void testGetFavoriteRecipients() throws Exception {
        FavoriteGetResponse response = FavoriteGetResponse.builder()
                .recipientName("John Doe")
                .build();

        when(service.getFavoriteRecipients(anyString())).thenReturn(Collections.singletonList(response));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/favorites")
                        .param("account-number", "123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipientName").value("John Doe"));
    }

    @Test
    void testDeleteFavorite() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/favorites")
                        .param("account-number", "123456")
                        .param("recipient-account-number", "654321"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Favorite recipient 654321 deleted successfully"));
    }
}
