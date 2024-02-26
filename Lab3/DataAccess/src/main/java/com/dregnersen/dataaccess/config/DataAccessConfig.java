package com.dregnersen.dataaccess.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.dregnersen.dataaccess.entities"})
@EnableJpaRepositories(basePackages = {"com.dregnersen.dataaccess.repositories"})
public class DataAccessConfig {
}
