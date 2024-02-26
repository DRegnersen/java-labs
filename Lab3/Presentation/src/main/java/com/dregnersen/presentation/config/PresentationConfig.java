package com.dregnersen.presentation.config;

import com.dregnersen.application.config.ApplicationConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.dregnersen.presentation.controllers"})
@Import(ApplicationConfig.class)
public class PresentationConfig {
}
