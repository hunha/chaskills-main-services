package com.realife.services.realife_services.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class Application {
	
	public static void main(String[] args) {
		// Will configure using users-server.yml
		System.setProperty("spring.config.name", "users-server");
		
		SpringApplication.run(Application.class, args);
	}
}
