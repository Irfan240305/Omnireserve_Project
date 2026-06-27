package com.omnireserve;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.retry.annotation.EnableRetry;


@SpringBootApplication
@EnableRetry
public class OmnireserveApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmnireserveApplication.class, args);
	}

}
