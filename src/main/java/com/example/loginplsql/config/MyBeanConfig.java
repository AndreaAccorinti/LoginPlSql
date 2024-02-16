package com.example.loginplsql.config;

import com.example.loginplsql.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanConfig {
    @Bean
    public UserServiceImpl loginService() {
        return new UserServiceImpl();
    }
}
