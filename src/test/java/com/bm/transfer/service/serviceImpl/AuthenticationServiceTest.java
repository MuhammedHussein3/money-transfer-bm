package com.bm.transfer.service.serviceImpl;

import com.bm.transfer.authentication.security.JwtService;
import com.bm.transfer.dto.enums.Branch;
import com.bm.transfer.dto.enums.Country;
import com.bm.transfer.dto.request.AuthenticationRequest;
import com.bm.transfer.dto.request.RegistrationRequest;
import com.bm.transfer.dto.response.AuthenticationResponse;
import com.bm.transfer.entity.Role;
import com.bm.transfer.entity.Token;
import com.bm.transfer.entity.User;
import com.bm.transfer.mapper.UserMapper;
import com.bm.transfer.repository.RoleRepository;
import com.bm.transfer.repository.TokenRepository;
import com.bm.transfer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {


    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserAccountServicedImpl accountService;

    @Mock
    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegistrationRequest request = RegistrationRequest.builder()
                .userName("testuser")
                .email("test@example.com")
                .password("password")
                .country(Country.SAR)
                .dateOfBirth(LocalDate.now())
                .branch(Branch.SOUTH)
                .build();


        User user = User.builder()
                .userName("testuser")
                .email("test@example.com")
                .password("encodedpassword")
                .balance(BigDecimal.valueOf(10000))
                .dateOfBirth(LocalDate.now())
                .country(Country.SAR)
                .accountNumber("123456")
                .accountLocked(false)
                .enabled(true)
                .roles(List.of())
                .branch(Branch.SOUTH)
                .build();

        when(roleRepository.findByName("USER")).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode("password")).thenReturn("encodedpassword");
        when(accountService.generateAccountNumber("US")).thenReturn("123456");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(), any(User.class))).thenReturn("jwtToken");
        when(tokenRepository.save(any(Token.class))).thenReturn(new Token());

        AuthenticationResponse response = authenticationService.register(request);

        assertEquals("123456", response.getAccountNumber());
        assertEquals("jwtToken", response.getToken());
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest request =
                AuthenticationRequest.builder()
                        .email("test@example.com")
                        .password("password")
                                .build();


        User user = User.builder()
                .userName("testuser")
                .email("test@example.com")
                .accountNumber("123456")
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(jwtService.generateToken(any(), any(User.class))).thenReturn("jwtToken");
        when(tokenRepository.save(any(Token.class))).thenReturn(new Token());

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertEquals("123456", response.getAccountNumber());
        assertEquals("jwtToken", response.getToken());
    }

    @Test
    void testLogout() {
        // Arrange
        Token token = new Token();
        token.setToken("token");
        token.setInvalidated(false);

        // Mock repository behavior
        when(tokenRepository.findByToken("token")).thenReturn(Optional.of(token));

        // Act
        authenticationService.logout("token");

        // Assert
        verify(tokenRepository, times(1)).save(token);
        assertEquals(true, token.isInvalidated(), "Token should be marked as invalidated");
    }
}
