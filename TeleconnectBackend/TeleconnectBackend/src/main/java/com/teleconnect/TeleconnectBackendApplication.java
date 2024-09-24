package com.teleconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TeleconnectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeleconnectBackendApplication.class, args);
	}

}