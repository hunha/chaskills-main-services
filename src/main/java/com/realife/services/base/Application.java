package com.realife.services.base;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@ComponentScan(basePackages = { "com.realife.services" })
public class Application {
	
	public static void main(String[] args) {
		// Will configure using main-services.yml
//		System.setProperty("spring.config.name", "main-services");
		
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
}
