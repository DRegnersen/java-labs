package com.dregnersen.security.presentation.config;

import com.dregnersen.presentation.config.PresentationConfig;
import com.dregnersen.security.application.config.SecuredApplicationConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.dregnersen.security.presentation.controllers"})
@Import({SecuredApplicationConfig.class, PresentationConfig.class})
public class SecuredPresentationConfig {
}
