package com.example.loginplsql.controllers;

import com.example.loginplsql.daos.PresenzaRepository;
import com.example.loginplsql.models.AttendanceResponse;
import com.example.loginplsql.models.LoginResponse;
import com.example.loginplsql.models.Presenza;
import com.example.loginplsql.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@RestController
public class AttendanceController {
    @Autowired
    PresenzaRepository daoAttendance;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Logger log = LoggerFactory.getLogger(AttendanceController.class);

    String DELETE_QUERY = "DELETE FROM attendance WHERE MONTH(date_attendance) = MONTH(CURDATE()) AND id_user = ?";

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

    @PostMapping("/add-attendances")
    ResponseEntity<AttendanceResponse> insertAttendances(@RequestHeader LoginResponse response, @RequestBody List<Presenza> attendances) {
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        Integer idUser;
        try {
            if (userService.isLogged() && userService.checkToken(response.getResponse()) && attendances != null && !attendances.isEmpty()) {
                idUser = attendances.getFirst().getUsername().getId();
                if (idUser != null ) {
                    if (this.deleteRecordsForCurrentUser(idUser) > 0) {
                        log.info("DELETE OK!");
                    } else {
                        log.info("DELETE ERROR!");
                    }
                }
                List<Presenza> savedAttendances = daoAttendance.saveAll(attendances);
                attendanceResponse.setAttendanceList(savedAttendances);
                attendanceResponse.setResponse("OK");
                return ResponseEntity.ok(attendanceResponse);
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
        attendanceResponse.setResponse("UNAUTHORIZED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(attendanceResponse);
    }

    public int deleteRecordsForCurrentUser(Integer userId) {
        return jdbcTemplate.update(DELETE_QUERY, userId);
    }
}
