package com.example.loginplsql.controllers;

import com.example.loginplsql.daos.PresenzaRepository;
import com.example.loginplsql.models.AttendanceResponse;
import com.example.loginplsql.models.LoginResponse;
import com.example.loginplsql.models.Presenza;
import com.example.loginplsql.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttendanceController {
    @Autowired
    PresenzaRepository daoAttendance;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/attendance_list_systmp")
    ResponseEntity<AttendanceResponse> getAttendanceFromSysTmp(@RequestHeader LoginResponse headers) {
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        if (userService.isLogged() && userService.checkToken(headers.getResponse())) {
            attendanceResponse.setAttendanceList(this.daoAttendance.getSysTimestampMounth());
            attendanceResponse.setResponse("OK");
            return ResponseEntity.ok(attendanceResponse);
        }
        attendanceResponse.setResponse("non fare il furbo!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(attendanceResponse);
    }

    @PostMapping("/attendance-from-month")
    ResponseEntity<AttendanceResponse> getAttendanceFromMonth(@RequestHeader LoginResponse response,@RequestBody String month) {
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        if (userService.isLogged() && userService.checkToken(response.getResponse())) {
            attendanceResponse.setAttendanceList(this.daoAttendance.getSysTimestampMounth());
            attendanceResponse.setResponse("OK");
            return ResponseEntity.ok(attendanceResponse);
        }
        attendanceResponse.setResponse("UNAUTHORIZED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(attendanceResponse);
    }
}
