package br.com.systemcore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class BusinessSystemCore {

	public static void main(String[] args) {
		SpringApplication.run(BusinessSystemCore.class, args);
	}

}
