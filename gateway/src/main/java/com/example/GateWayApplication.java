package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
