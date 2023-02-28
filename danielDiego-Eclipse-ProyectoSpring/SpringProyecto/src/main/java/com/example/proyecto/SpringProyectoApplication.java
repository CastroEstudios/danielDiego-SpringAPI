package com.example.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@EntityScan("com.example.modelo")
@ComponentScan("com.example.controlador")
@EnableJpaRepositories("com.example.repositorio")

public class SpringProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringProyectoApplication.class, args);
	}

}
