package com.example.loginplsql.controllers;

import com.example.loginplsql.daos.UserRepository;
import com.example.loginplsql.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("user", new User());
        return "main";
    }
}
