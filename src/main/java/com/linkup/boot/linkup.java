package com.linkup.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.linkup")
@EntityScan("com.linkup.model")
@EnableJpaRepositories(basePackages = "com.linkup.dao")
public class linkup extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(linkup.class, args);
	}

}
