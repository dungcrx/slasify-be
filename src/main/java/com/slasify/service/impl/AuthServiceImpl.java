package com.slasify.service.impl;

import com.slasify.dto.request.UserRegisterReq;
import com.slasify.dto.response.UserRegisterRes;
import com.slasify.repository.UserRepository;
import com.slasify.service.AuthService;
import com.slasify.utils.JwtUtil;
import com.slasify.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.slasify.entity.User;
import com.slasify.exception.*;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserRegisterRes register(UserRegisterReq userRegisterReq) {
        if(StringUtils.isEmpty(userRegisterReq.getEmail()) && StringUtils.isEmpty(userRegisterReq.getUserName())) {
            throw new IllegalArgumentException("Username could not be empty");
        }

        if(StringUtils.isEmpty(userRegisterReq.getPasswordHash())) {
            throw new IllegalArgumentException("Password could not be empty");
        }

        if (StringUtils.isNotEmpty(userRegisterReq.getUserName())
                &&  userRepository.existsByUsername(userRegisterReq.getUserName())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (StringUtils.isNotEmpty(userRegisterReq.getEmail())
                && userRepository.existsByEmail(userRegisterReq.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = User.builder()
                .username(StringUtils.isNotEmpty(userRegisterReq.getUserName()) ? userRegisterReq.getUserName() : null)
                .email(StringUtils.isNotEmpty(userRegisterReq.getEmail()) ? userRegisterReq.getEmail() : null)
                .passwordHash(passwordEncoder.encode(userRegisterReq.getPasswordHash())) // Hash the password before saving
                .build();

        user = userRepository.save(user);
        return UserRegisterRes.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }

    @Override
    public String authenticate(final String identifier,
                               final String password,
                               final boolean rememberMe) {
        User user = userRepository.findByUsernameOrEmail(identifier, identifier)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new InvalidPasswordException("Invalid password");
        }

        return jwtUtil.generateToken(user.getUsername(), rememberMe);
    }

    public Long getUserId() {
        String username = SecurityUtil.getCurrentUsername();
        if (username == null) {
            throw new UnauthorizedAccessException("User is not authenticated");
        }

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.getId();
    }


}

