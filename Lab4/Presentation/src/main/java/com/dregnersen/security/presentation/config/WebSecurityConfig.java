package com.dregnersen.security.presentation.config;

import com.dregnersen.security.application.services.user.UserService;
import com.dregnersen.security.dataaccess.Role;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "basicAuth", scheme = "basic")
public class WebSecurityConfig {
    private static final String[] SWAGGER_ROUTES = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/newcomer/**").anonymous()
                        .requestMatchers("/user/**").hasRole(Role.ADMIN.toString())
                        .requestMatchers("/cat/**").hasRole(Role.ADMIN.toString())
                        .requestMatchers("/owner/**").hasRole(Role.ADMIN.toString())
                        .requestMatchers("/account/**").hasRole(Role.USER.toString())
                        .requestMatchers(SWAGGER_ROUTES).permitAll())
                .csrf().disable()
                .formLogin().disable()
                .httpBasic()
                .and().build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserService service, PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder);
    }
}
