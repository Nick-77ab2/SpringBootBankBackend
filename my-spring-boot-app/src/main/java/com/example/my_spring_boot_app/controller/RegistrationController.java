package com.example.my_spring_boot_app.controller;

import com.example.my_spring_boot_app.security.User;
import com.example.my_spring_boot_app.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String registerUser(@RequestBody User user){
        userService.saveUser(user);
        return "User registered successfully";
    }
}
