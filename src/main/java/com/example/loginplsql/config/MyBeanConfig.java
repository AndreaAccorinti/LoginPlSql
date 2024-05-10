package com.example.loginplsql.config;

import com.example.loginplsql.daos.PresenzaRepository;
import com.example.loginplsql.daos.UserRepository;
import com.example.loginplsql.services.AttendanceService;
import com.example.loginplsql.services.PdfGeneratorService;
import com.example.loginplsql.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanConfig {
    @Bean
    public UserServiceImpl loginService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public AttendanceService attendanceService(PresenzaRepository presenzaRepository) {
        return new AttendanceService(presenzaRepository);
    }

    @Bean
    public PdfGeneratorService pdfGeneratorService() {
        return new PdfGeneratorService();
    }
}
