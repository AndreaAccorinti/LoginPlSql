package com.example.loginplsql.controllers;
import com.example.loginplsql.daos.UserRepository;
import com.example.loginplsql.exception.UserNotFoundException;
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
    @Autowired
    UserRepository daoUser;

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users_list")
    List<User> getAllUsers() {
        return daoUser.findAll();
    }

    @PostMapping("/add-user")
    User newUser(@RequestBody User user){
        return this.daoUser.save(user);
    }

    @GetMapping("/getUser/{id}")
    User getUser(@PathVariable int id) {
        return this.daoUser.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/getUser/{username}")
    User getUserByUsername(@PathVariable String username) {
        return this.daoUser.findByUsername(username);
    }

    @DeleteMapping("/delete-user/{id}")
    void deleteUser(@PathVariable int id) {
        this.daoUser.deleteById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User request) {
        User user = daoUser.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid username or password!"));
        }
        userService.authenticate(user);
        return ResponseEntity.ok(new LoginResponse("Login successful", userService.getToken()));
    }

}
