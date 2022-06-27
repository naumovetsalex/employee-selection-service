package com.service.select.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.service.select.employee.repository.jpa"})
@ComponentScan({"com.sap.cloud.sdk", "com.service.select.employee"})
@ServletComponentScan({"com.sap.cloud.sdk", "com.service.select.employee"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
