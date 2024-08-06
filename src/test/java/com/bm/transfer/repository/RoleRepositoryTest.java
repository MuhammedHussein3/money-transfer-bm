package com.bm.transfer.repository;

import com.bm.transfer.authentication.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .id(1)
                .name("ROLE_USER")
                .users(Collections.emptyList())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldFindByName() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));

        Optional<Role> foundRole = roleRepository.findByName("ROLE_USER");
        assertTrue(foundRole.isPresent());
        assertEquals("ROLE_USER", foundRole.get().getName());
    }
}
