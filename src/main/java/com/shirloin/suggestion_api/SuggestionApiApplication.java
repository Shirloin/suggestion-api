package com.shirloin.suggestion_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SuggestionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuggestionApiApplication.class, args);
	}

}
