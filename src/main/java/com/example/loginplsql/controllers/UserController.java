package com.example.loginplsql.controllers;
import com.example.loginplsql.daos.UserRepository;
import com.example.loginplsql.exception.UserNotFoundException;
import com.example.loginplsql.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
@RestController
public class UserController {
    @Autowired
    UserRepository daoUser;

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

    @DeleteMapping("/delete-user/{id}")
    void deleteUser(@PathVariable int id) {
        this.daoUser.deleteById(id);
    }

}
