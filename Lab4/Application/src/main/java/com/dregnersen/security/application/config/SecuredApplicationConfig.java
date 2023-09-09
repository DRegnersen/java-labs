package com.dregnersen.security.application.config;

import com.dregnersen.dataaccess.config.DataAccessConfig;
import com.dregnersen.security.application.services.user.UserServiceSettings;
import com.dregnersen.security.dataaccess.config.SecuredDataAccessConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = {"com.dregnersen.security.application.services"})
@Import({SecuredDataAccessConfig.class, DataAccessConfig.class})
public class SecuredApplicationConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserServiceSettings userServiceSettings(){
        return new UserServiceSettings("root", "123654");
    }
}
