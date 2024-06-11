package de.thws.portofoile3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "de.thws.portofoile3.repository")
public class Portofoile3Application {

	public static void main(String[] args) {
		SpringApplication.run(Portofoile3Application.class, args);
	}



}
