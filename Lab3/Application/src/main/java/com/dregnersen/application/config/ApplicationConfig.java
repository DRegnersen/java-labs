package com.dregnersen.application.config;

import com.dregnersen.dataaccess.config.DataAccessConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.dregnersen.application.services"})
@Import({DataAccessConfig.class})
public class ApplicationConfig {
}
