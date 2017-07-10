package com.realife.services.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EntityScan("com.realife.services.domains")
@EnableJpaRepositories("com.realife.services.repositories")
@EnableTransactionManagement
public class ApplicationConfiguration {
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
