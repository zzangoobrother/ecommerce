package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ConfigurationPropertiesScan(basePackages = {"com.example"})
@SpringBootApplication(scanBasePackages = "com.example")
public class QueueApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueueApiApplication.class, args);
    }
}
