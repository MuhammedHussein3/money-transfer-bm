package com.bm.transfer.repository;

import com.bm.transfer.dto.enums.Branch;
import com.bm.transfer.dto.enums.Country;
import com.bm.transfer.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .userName("muhammad_Hussein")
                .password("13?23fadFAd")
                .email("muhammadhussein2312@gmail.com")
                .accountNumber("981-981345")
                .balance(BigDecimal.valueOf(10000.00))
                .branch(Branch.SOUTH)
                .country(Country.EGP)
                .password("")
                .dateOfBirth(LocalDate.now())
                .enabled(false)
                .accountLocked(false)
                .transactions(new ArrayList<>())
                .roles(new ArrayList<>())
                .favorites(new ArrayList<>())
                .build();
    }

    @Test
    void shouldFindUserByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByEmail("muhammadhussein2312@gmail.com");
        assertTrue(foundUser.isPresent());
        assertEquals("muhammad_Hussein", foundUser.get().getUserName());
    }

    @Test
    void shouldGetCurrentBalance() {
        when(userRepository.getCurrentBalance(anyString())).thenReturn(Optional.of(user.getBalance()));

        Optional<BigDecimal> balance = userRepository.getCurrentBalance("981-981345");
        assertTrue(balance.isPresent());
        assertEquals(BigDecimal.valueOf(10000.00), balance.get());
    }

    @Test
    void shouldFindUserByAccountNumberAndUserName() {
        when(userRepository.getUserByAccountNumberAndUserName(anyString(), anyString())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.getUserByAccountNumberAndUserName("981-981345", "muhammad_Hussein");
        assertTrue(foundUser.isPresent());
        assertEquals("muhammadhussein2312@gmail.com", foundUser.get().getEmail());
    }

    @Test
    void shouldFindUserByAccountNumber() {
        when(userRepository.getUserByAccountNumber(anyString())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.getUserByAccountNumber("981-981345");
        assertTrue(foundUser.isPresent());
        assertEquals("muhammad_Hussein", foundUser.get().getUserName());
    }


}
