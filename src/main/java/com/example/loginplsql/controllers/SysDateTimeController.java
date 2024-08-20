package com.example.loginplsql.controllers;

import com.example.loginplsql.Utils.Utils;
import com.example.loginplsql.daos.SysDateTimeRepository;
import com.example.loginplsql.models.LoginResponse;
import com.example.loginplsql.models.Month;
import com.example.loginplsql.models.MonthResponse;
import com.example.loginplsql.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sysdatetime")
public class SysDateTimeController {
    @Autowired
    SysDateTimeRepository sysDateTimeRepository;

    @Autowired
    UserServiceImpl userService;

    Logger log = LoggerFactory.getLogger(SysDateTimeController.class);

    @CrossOrigin(origins = Utils.CORSURL)
    @GetMapping("/month-list")
    ResponseEntity<MonthResponse> getMonthList(@RequestHeader LoginResponse loginResponse) {
        MonthResponse monthResponse = new MonthResponse();
        List<Month> monthList = new ArrayList<>();
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int m = now.getMonth().getValue();
        monthList.add(new Month(0, now.getMonth().name(), now.format(dateTimeFormatter)));
        for (int i = 1; i < m; i++) {
            LocalDate month = now.minusMonths(i);
            Month newMonth = new Month(i, month.getMonth().name(), month.format(dateTimeFormatter));
            monthList.add(newMonth);
        }
        if (userService.isLogged() && userService.checkToken(loginResponse.getResponse())) {
            monthResponse.setMonthList(monthList.reversed());
            monthResponse.setResponse("OK");
            return ResponseEntity.ok(monthResponse);
        }

        monthResponse.setResponse("UNAUTHORIZED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(monthResponse);

    }
}
