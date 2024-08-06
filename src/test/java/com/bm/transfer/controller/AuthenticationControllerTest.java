package com.bm.transfer.controller;

import com.bm.transfer.dto.request.AuthenticationRequest;
import com.bm.transfer.dto.request.RegistrationRequest;
import com.bm.transfer.dto.response.AuthenticationResponse;
import com.bm.transfer.service.serviceImpl.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService service;

    @InjectMocks
    private AuthenticationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() throws Exception {
        RegistrationRequest request = RegistrationRequest.builder()
                .userName("user")
                .password("password")
                .build();

        AuthenticationResponse response =
                AuthenticationResponse.builder()
                                .token("token")
                                        .build();

        when(service.register(any(RegistrationRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.token").value("token"));
    }

//    @Test
//    void testAuthenticate() throws Exception {
//        AuthenticationRequest request = AuthenticationRequest.builder()
//                .email("user")
//                .password("password")
//                .build();
//
//
//
//        when(service.authenticate(any(AuthenticationRequest.class))).thenReturn(response);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("token"));
//    }

    @Test
    void testLogout() throws Exception {
        String token = "token";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/logout")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Logged out successfully."));
    }
}
