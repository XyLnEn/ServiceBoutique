package com.alma.boutique.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by thomas on 25/10/16.
 */
@SpringBootApplication
public class Presentation {
	public static void main(String args[]) {
		SpringApplication.run(Presentation.class);
	}
}
