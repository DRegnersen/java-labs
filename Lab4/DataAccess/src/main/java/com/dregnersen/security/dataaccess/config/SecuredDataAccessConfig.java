package com.dregnersen.security.dataaccess.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.dregnersen.security.dataaccess.entities"})
@EnableJpaRepositories(basePackages = {"com.dregnersen.security.dataaccess.repositories"})
public class SecuredDataAccessConfig {
}
