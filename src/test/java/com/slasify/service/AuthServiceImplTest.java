package com.slasify.service;

import com.slasify.dto.request.UserRegisterReq;
import com.slasify.dto.response.UserRegisterRes;
import com.slasify.entity.User;
import com.slasify.repository.UserRepository;
import com.slasify.service.impl.AuthServiceImpl;
import com.slasify.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil;

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(userRepository, passwordEncoder, jwtUtil);
    }

    @Test
    void testRegisterWithEmptyUsernameAndEmail() {
        UserRegisterReq request = new UserRegisterReq("", "", "password123");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });

        assertEquals("Username could not be empty", exception.getMessage());
    }

    @Test
    void testRegisterWithEmptyPassword() {
        UserRegisterReq request = new UserRegisterReq("user1", "user1@example.com", "");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });

        assertEquals("Password could not be empty", exception.getMessage());
    }

    @Test
    void testRegisterWithExistingUsername() {
        UserRegisterReq request = new UserRegisterReq("user1", "user1@example.com", "password123");
        when(userRepository.existsByUsername("user1")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });

        assertEquals("Username is already taken", exception.getMessage());
    }

    @Test
    void testRegisterWithExistingEmail() {
        UserRegisterReq request = new UserRegisterReq("user1", "user1@example.com", "password123");
        when(userRepository.existsByEmail("user1@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });

        assertEquals("Email is already registered", exception.getMessage());
    }

    @Test
    void testRegisterSuccess() {
        UserRegisterReq request = new UserRegisterReq("user1", "user1@example.com", "password123");
        when(userRepository.existsByUsername("user1")).thenReturn(false);
        when(userRepository.existsByEmail("user1@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setEmail("user1@example.com");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserRegisterRes response = authService.register(request);

        assertNotNull(response);
        assertEquals("user1", response.getUserName());
        assertEquals("user1@example.com", response.getEmail());
        assertEquals(1L, response.getId());
    }
}

