package com.example.SpringBootAssignment1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.example.SpringBootAssignment1.Repository")
@SpringBootApplication
public class SpringBootAssignment1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAssignment1Application.class, args);
	}

}
