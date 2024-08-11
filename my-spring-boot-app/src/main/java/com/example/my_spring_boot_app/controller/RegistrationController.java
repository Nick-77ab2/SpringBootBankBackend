package com.example.my_spring_boot_app.controller;

import com.example.my_spring_boot_app.security.CustomUserDetailsService;
import com.example.my_spring_boot_app.security.User;
import com.example.my_spring_boot_app.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping
    public String registerUser(@RequestBody User user){
        try {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            return "User with this email already exists.";
        }catch(UsernameNotFoundException e){
                if(checkPassword(user.getPassword())) {
                    userService.saveUser(user);
                    return "success";
                }
                else{
                    return "password must contain an uppercase letter and a special character.";
                }
        }
    }

    public boolean checkPassword(String password){
        if (password.length()<8)
            return false;
        //Courtesy of geeksforgeeks

        String regex = "^(?=.*[a-z])(?=."
                + "*[A-Z])(?=.*\\d)"
                + "(?=.*[-+_!@#$%^&*., ?]).+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
