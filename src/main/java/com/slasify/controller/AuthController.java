package com.slasify.controller;

import com.slasify.dto.request.UserLoginReq;
import com.slasify.dto.request.UserRegisterReq;
import com.slasify.dto.response.UserRegisterRes;
import com.slasify.service.AuthService;
import com.slasify.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterReq userRegisterReq) {
        UserRegisterRes userRegisterRes = authService.register(userRegisterReq);
        URI location = URI.create("/users/" + userRegisterRes.getId());
        return ResponseEntity.created(location).body(userRegisterRes);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginReq loginRequest, HttpServletResponse response) {
        boolean rememberMe = loginRequest.isRememberMe();
        String userName = loginRequest.getIdentifier();
        String token = authService.authenticate(
                userName,
                loginRequest.getPassword(),
                rememberMe
        );
        log.info("User userName {} with rememberme = {} ", userName ,rememberMe);


        Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");

        if (rememberMe) {
            // Set cookie to expire after the token expires
            jwtCookie.setMaxAge((int) (JwtUtil.REMEMBER_ME_EXPIRATION_TIME / 1000));
        } else {
            // Set cookie to be deleted when the browser is closed
            jwtCookie.setMaxAge(-1);
        }

        // Add cookie to response
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(Map.of("token", token));
    }
}

