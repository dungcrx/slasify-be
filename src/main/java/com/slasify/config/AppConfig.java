package com.slasify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.slasify.repository")
public class AppConfig {

    @Value("${jwt.secret}")
    public String secretKey;

    @Value("${jwt.expiration}")
    public static long expirationTime; // in milliseconds
}
