package com.example.geeks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GeeksApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeeksApplication.class, args);
	}

}
