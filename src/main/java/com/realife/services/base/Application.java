package com.realife.services.base;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@ComponentScan(basePackages = { "com.realife.services.controllers" })
@EntityScan("com.realife.services.domains")
@EnableJpaRepositories("com.realife.services.repositories")
public class Application {
	
	public static void main(String[] args) {
		// Will configure using main-services.yml
//		System.setProperty("spring.config.name", "main-services");
		
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
}
