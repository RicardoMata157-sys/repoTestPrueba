package com.mx.test.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.mx.test")
@EnableJpaRepositories(basePackages = "com.mx.test.repository")
@EntityScan(basePackages = "com.mx.test.entity")
public class ClassMain {
    public static void main(String[] args) {
        SpringApplication.run(ClassMain.class, args);
    }
}