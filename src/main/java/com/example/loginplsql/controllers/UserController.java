package com.example.loginplsql.controllers;

import com.example.loginplsql.models.LoginResponse;
import com.example.loginplsql.models.User;
import com.example.loginplsql.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users_list")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            return ResponseEntity.ok(userService.findAllUsers());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/add-user")
    public ResponseEntity<User> addUser(@RequestBody User user, @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            return ResponseEntity.ok(userService.addUser(user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id, @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/getUser/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username, @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            User user = userService.findUserByUsername(username);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id, @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody User request) {
        return userService.login(request);
    }

    @PostMapping("/update-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails, @RequestHeader LoginResponse response) {
        if (userService.verifyUserAndToken(response.getResponse())) {
            return ResponseEntity.ok(userService.updateUser(id, userDetails));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
