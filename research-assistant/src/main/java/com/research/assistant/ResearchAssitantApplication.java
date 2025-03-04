package com.research.assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ResearchAssitantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearchAssitantApplication.class, args);
	}

}
