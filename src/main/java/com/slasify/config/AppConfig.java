package com.slasify.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.slasify.repository")
@ComponentScan(basePackages = "com.slasify")
public class AppConfig {
}
