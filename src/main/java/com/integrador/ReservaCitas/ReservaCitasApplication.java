package com.integrador.ReservaCitas;

import com.integrador.ReservaCitas.utils.SQLConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ReservaCitasApplication {

	public static void main(String[] args) {
		try{
			SQLConnection.createTables();
		} catch (Exception e){

		}
		SpringApplication.run(ReservaCitasApplication.class, args);
	}

	@GetMapping
	public String helloWorld(){
		return "Hello World!";
	}

}
