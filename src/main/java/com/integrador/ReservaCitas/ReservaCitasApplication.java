package com.integrador.ReservaCitas;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
