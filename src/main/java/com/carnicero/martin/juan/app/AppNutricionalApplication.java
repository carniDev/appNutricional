package com.carnicero.martin.juan.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class AppNutricionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppNutricionalApplication.class, args);
	}

}
