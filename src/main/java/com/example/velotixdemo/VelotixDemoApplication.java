package com.example.velotixdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class VelotixDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(VelotixDemoApplication.class, args);
    }
}
