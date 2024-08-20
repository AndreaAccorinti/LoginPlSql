package com.example.loginplsql.controllers;

import com.example.loginplsql.daos.PresenzaRepository;
import com.example.loginplsql.models.LoginResponse;
import com.example.loginplsql.models.User;
import com.example.loginplsql.services.AttendanceService;
import com.example.loginplsql.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.loginplsql.Utils.Utils;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserServiceImpl userService;
    private final AttendanceService attendanceService;
    private final PresenzaRepository daoAttendance;

    @Autowired
    public UserController(UserServiceImpl userService,
                          AttendanceService attendanceService,
                          PresenzaRepository daoAttendance) {
        this.userService = userService;
        this.attendanceService = attendanceService;
        this.daoAttendance = daoAttendance;
    }


    @CrossOrigin(origins = Utils.CORSURL)
    @GetMapping("/users_list")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            return ResponseEntity.ok(userService.findAllUsers());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    @CrossOrigin(origins = Utils.CORSURL)
    @GetMapping("/get-total-monthly-working-days")
    public ResponseEntity<Integer> getTotalMonthlyWorkingDays(
            @RequestParam String date,
            @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            int days = daoAttendance.getTotalMonthlyWorkingDays(date);
            return ResponseEntity.ok(days);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @CrossOrigin(origins = Utils.CORSURL)
    @PostMapping("/get-user-monthly-attendance-for-description")
    public ResponseEntity<Integer> getUserMonthlyAttendance(
            @RequestParam("userId") int userId,
            @RequestParam("description") String description,
            @RequestParam("month") int month,
            @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            int attendanceDays = daoAttendance.
                    getTotDayAttendanceFromUserDescription(userId, description, month);
            return ResponseEntity.ok(attendanceDays);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @CrossOrigin(origins = Utils.CORSURL)
    @PostMapping("/check-user-monthly-status")
    public ResponseEntity<Boolean> checkUserMonthlyStatus(
            @RequestParam("userId") int userId,
            @RequestParam("yearMonth") String yearMonth,
            @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            boolean status = attendanceService.status(userId, yearMonth);
            return ResponseEntity.ok(status);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @CrossOrigin(origins = Utils.CORSURL)
    @PostMapping("/add-user")
    public ResponseEntity<User> addUser(@RequestBody User user, @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            return ResponseEntity.ok(userService.addUser(user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @CrossOrigin(origins = Utils.CORSURL)
    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id, @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @CrossOrigin(origins = Utils.CORSURL)
    @GetMapping("/getUser/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username, @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            User user = userService.findUserByUsername(username);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @CrossOrigin(origins = Utils.CORSURL)
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id, @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @CrossOrigin(origins = Utils.CORSURL)
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody User request) {
        return userService.login(request);
    }
    @CrossOrigin(origins = Utils.CORSURL)
    @PostMapping("/update-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails, @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            return ResponseEntity.ok(userService.updateUser(id, userDetails));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
