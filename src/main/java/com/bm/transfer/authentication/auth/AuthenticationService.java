package com.bm.transfer.authentication.auth;

import com.bm.transfer.account.service.UserAccountServicedImpl;
import com.bm.transfer.authentication.role.RoleRepository;
import com.bm.transfer.authentication.security.JwtService;
import com.bm.transfer.authentication.user.Token;
import com.bm.transfer.authentication.user.TokenRepository;
import com.bm.transfer.authentication.user.User;
import com.bm.transfer.authentication.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private static final String USER_ROLE = "USER";
    private static final String CHARACTERS = "0123456789";
    private static final int TOKEN_LENGTH = 8;
    private static final int TOKEN_EXPIRATION_MINUTES = 180;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserAccountServicedImpl accountService;

    public AuthenticationResponse register(RegistrationRequest request) {

        var userRole = roleRepository.findByName(USER_ROLE)
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));

        var user = User.builder()
                .userName(request.userName)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .balance(BigDecimal.valueOf(10000))
                .dateOfBirth(request.getDateOfBirth())
                .country(request.getCountry())
                .accountNumber(accountService.generateAccountNumber(request.getCountry().toString()))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(userRole))
                .build();

       var savedUser =  userRepository.save(user);
        var claims = new HashMap<String, Object>();
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);
        Token token = Token
                .builder()
                .token(jwtToken)
                .createdAt(LocalDateTime.now())
                .invalidated(false)
                .user(user)
                        .build();
        tokenRepository.save(token);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .accountNumber(savedUser.getAccountNumber())
                .user_id(savedUser.getId())
                .build();

    }


    public String authenticate(AuthenticationRequest request) {
        logger.info("Authenticating user with email: {}", request.getEmail());
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);
        Token token = Token
                .builder()
                .token(jwtToken)
                .createdAt(LocalDateTime.now())
                .invalidated(false)
                .user(user)
                .build();
        tokenRepository.save(token);
        return token.getToken();
    }







    public void logout(String token) {;
        System.out.println("///////////////////////////////////////////////////////////////////////////////////////");
        System.out.println(token);
        Optional<Token> savedToken = tokenRepository.findByToken(token);
        System.out.println(savedToken.toString());
        savedToken.ifPresent(t -> {
            t.setInvalidated(true);
            tokenRepository.save(t);
        });
    }
}
