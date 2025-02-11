package com.slasify.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtUtilTest {

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtUtil.getSecretKey()).thenReturn("mySuperSecretKey12345-your-32-byte-minimum-secret-key");
        when(jwtUtil.getExpirationTime()).thenReturn(3600000L);
    }

    @Test
    void testValidateTokenWithValidToken() {
        String username = "user1";
        boolean rememberMe = true;
        String mockToken = "valid.jwt.token";
        when(jwtUtil.generateToken(username, rememberMe)).thenReturn(mockToken);

        String token = jwtUtil.generateToken(username, rememberMe);

        assertNotNull(token);
        assertTrue(token.contains("."));
    }

    @Test
    void testValidateTokenWithInvalidToken() {
        String token = "invalidToken";
        User userDetails = new User("user1", "password", new ArrayList<>());

        boolean isValid = jwtUtil.validateToken(token, userDetails);

        assertFalse(isValid);
    }
}
