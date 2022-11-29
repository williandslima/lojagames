package com.lojaGames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class })
public class LojaGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaGamesApplication.class, args);
	}

}
