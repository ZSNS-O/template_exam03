package de.thws.portfolio3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "de.thws.portofoile3.repository")
public class Portofoile3Application {

	public static void main(String[] args) {
		SpringApplication.run(Portofoile3Application.class, args);
	}



}
