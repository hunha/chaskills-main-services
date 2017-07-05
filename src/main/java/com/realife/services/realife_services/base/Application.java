package com.realife.services.realife_services.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.realife.services.realife_services.controlleres" })
public class Application {
	
	public static void main(String[] args) {
		// Will configure using main-services.yml
//		System.setProperty("spring.config.name", "main-services");
		
		SpringApplication.run(Application.class, args);
	}
}
