package com.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TaskManagementSystemFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementSystemFrontEndApplication.class, args);
	}
	
	@Configuration
	 class AppConfig {

	    @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
	}
}
