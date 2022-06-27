package com.service.select.employee.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = { "com.service.select.employee.repository.jpa" })
@EnableJpaRepositories(basePackages = {"com.service.select.employee.repository.jpa"})
@EntityScan(basePackages = {"com.service.select.employee.**"})
public class JPAConfig {

}
