package com.project.theatermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.project.theatermanager"})
public class TheaterManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheaterManagerApplication.class, args);
	}

}
