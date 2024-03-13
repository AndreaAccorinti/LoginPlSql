package com.example.loginplsql.controllers;

import com.example.loginplsql.daos.SysDateTimeRepository;
import com.example.loginplsql.models.LoginResponse;
import com.example.loginplsql.models.MonthResponse;
import com.example.loginplsql.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysdatetime")
public class SysDateTimeController {
    @Autowired
    SysDateTimeRepository sysDateTimeRepository;

    @Autowired
    UserServiceImpl userService;

    Logger log = LoggerFactory.getLogger(SysDateTimeController.class);

    @GetMapping("/month-list")
    ResponseEntity<MonthResponse> getMonthList(@RequestHeader LoginResponse loginResponse) {
        MonthResponse monthResponse = new MonthResponse();
        if (userService.isLogged() && userService.checkToken(loginResponse.getResponse())) {
            try {
                monthResponse.setMonthList(sysDateTimeRepository.monthList());
                monthResponse.setResponse("OK");
                return ResponseEntity.ok(monthResponse);
            } catch (Exception e) {
                log.info(e.toString());
            }
        }
        monthResponse.setResponse("UNAUTHORIZED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(monthResponse);
    }
}
