package com.example.loginplsql.controllers;

import com.example.loginplsql.Utils.Utils;
import com.example.loginplsql.daos.PresenzaRepository;
import com.example.loginplsql.models.AttendanceResponse;
import com.example.loginplsql.models.LoginResponse;
import com.example.loginplsql.models.Presenza;
import com.example.loginplsql.models.PresenzeRequest;
import com.example.loginplsql.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AttendanceController {
    @Autowired
    PresenzaRepository daoAttendance;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Logger log = LoggerFactory.getLogger(AttendanceController.class);

    String DELETE_QUERY = "DELETE FROM attendance WHERE MONTH(date_attendance) = MONTH(CURDATE()) AND id_user = ?";
    @CrossOrigin(origins = Utils.CORSURL)
    @PostMapping("/attendance_list_systmp")
    ResponseEntity<AttendanceResponse> getAttendanceFromSysTmp(@RequestHeader LoginResponse headers) {
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        if (userService.isLogged() && userService.checkToken(headers.getResponse())) {
            attendanceResponse.setAttendanceList(this.daoAttendance.getSysTimestampMonth());
            attendanceResponse.setResponse("OK");
            return ResponseEntity.ok(attendanceResponse);
        }
        attendanceResponse.setResponse("non fare il furbo!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(attendanceResponse);
    }

    @CrossOrigin(origins = Utils.CORSURL)
    @PostMapping("/presenze")
    ResponseEntity<AttendanceResponse> getAttendanceFromUsrDate(@RequestHeader LoginResponse headers, @RequestBody PresenzeRequest presenzeRequest) {
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        try {
            if (userService.isLogged() && userService.checkToken(headers.getResponse())) {
                attendanceResponse.setAttendanceList(
                        this.daoAttendance.findByUserAndMonthAndYear(
                                presenzeRequest.getIdUser(),
                                presenzeRequest.getAnno(),
                                presenzeRequest.getMese()
                        )
                );
                attendanceResponse.setResponse("OK");
                return ResponseEntity.ok(attendanceResponse);
            }
            attendanceResponse.setResponse("Unauthorized access attempt!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(attendanceResponse);
        } catch (NullPointerException e) {
            attendanceResponse.setResponse("Invalid request: some required fields are null.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(attendanceResponse);
        }
    }
    @CrossOrigin(origins = Utils.CORSURL)
    @PostMapping("/attendance-from-month")
    ResponseEntity<AttendanceResponse> getAttendanceFromMonth(@RequestHeader LoginResponse response,@RequestBody String month) {
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        if (userService.isLogged() && userService.checkToken(response.getResponse())) {
            attendanceResponse.setAttendanceList(this.daoAttendance.getSysTimestampMonth());
            attendanceResponse.setResponse("OK");
            return ResponseEntity.ok(attendanceResponse);
        }
        attendanceResponse.setResponse("UNAUTHORIZED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(attendanceResponse);
    }
    @CrossOrigin(origins = Utils.CORSURL)
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
