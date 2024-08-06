package com.bm.transfer.repository;

import com.bm.transfer.entity.Token;
import com.bm.transfer.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenRepositoryTest {
    @Mock
    private TokenRepository tokenRepository;

    private Token token;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1L)
                .userName("testuser")
                .password("password")
                .email("testuser@example.com")
                .build();

        token = Token.builder()
                .id(1)
                .token("e3iaksoui2mrlkjalkdflkajsdiurj2k3rnmkdajdjfiuifojsdkjfaskdjfaisudfi2jlk3j42y37437hjehda7str23rdtadybsdbfq3t4j32r876cxhucuas9d89sdvs9dsa9fj8908c9dif9as98f9asdf0asd9fas")
                .createdAt(LocalDateTime.now())
                .user(user)
                .invalidated(false)
                .build();
    }

    @Test
    void shouldFindByToken() {
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));

        Optional<Token> foundToken = tokenRepository.findByToken("e3iaksoui2mrlkjalkdflkajsdiurj2k3rnmkdajdjfiuifojsdkjfaskdjfaisudfi2jlk3j42y37437hjehda7str23rdtadybsdbfq3t4j32r876cxhucuas9d89sdvs9dsa9fj8908c9dif9as98f9asdf0asd9fas");
        assertTrue(foundToken.isPresent());
        assertEquals("e3iaksoui2mrlkjalkdflkajsdiurj2k3rnmkdajdjfiuifojsdkjfaskdjfaisudfi2jlk3j42y37437hjehda7str23rdtadybsdbfq3t4j32r876cxhucuas9d89sdvs9dsa9fj8908c9dif9as98f9asdf0asd9fas", foundToken.get().getToken());
    }
}
