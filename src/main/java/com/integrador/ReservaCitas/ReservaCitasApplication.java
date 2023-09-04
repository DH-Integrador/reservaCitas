package com.integrador.ReservaCitas;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class ReservaCitasApplication {

	private static final Logger logger = Logger.getLogger(ReservaCitasApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ReservaCitasApplication.class, args);
	}

	@GetMapping
	public String helloWorld(){
		return "Hello World Dental Clinic!";
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		logger.info("Configurando CORS");
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*");
			}
		};
	}
}
