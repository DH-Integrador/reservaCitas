package com.integrador.ReservaCitas;

import com.integrador.ReservaCitas.utils.SQLConnection;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;

@SpringBootApplication
@RestController
public class ReservaCitasApplication {

	private static final Logger logger = Logger.getLogger(ReservaCitasApplication.class);
	public static void main(String[] args) {
		try{
			Connection connection = SQLConnection.getConnection();
			SQLConnection.createTables();
			logger.info("Se realizó la creación de tablas en la base de datos");
		} catch (Exception e){
			logger.error("Error al crear las tablas en la base de datos: " + e);
		}
		SpringApplication.run(ReservaCitasApplication.class, args);
	}

	@GetMapping
	public String helloWorld(){
		return "Hello World!";
	}

}
