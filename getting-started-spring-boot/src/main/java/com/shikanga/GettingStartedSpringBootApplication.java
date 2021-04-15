package com.shikanga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class GettingStartedSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GettingStartedSpringBootApplication.class, args);
	}

	@GetMapping
	public List<String> hello() {
		return List.of("Hello", "World");
	}

}
