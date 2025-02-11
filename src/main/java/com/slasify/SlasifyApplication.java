package com.slasify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.slasify")
public class SlasifyApplication {

	public static void main(String[] args) {

		SpringApplication.run(SlasifyApplication.class, args);
	}

}
