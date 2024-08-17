package com.example.my_spring_boot_app.services;

import com.example.my_spring_boot_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public User saveUser(User user){
        user.setPassword((passwordEncoder.encode(user.getPassword())));
        user.setVerificationToken(generateToken());
        user.setTokenExpiration(LocalDateTime.now().plusMinutes(10)); //Token is valid for 10m
        user.setAccount(5);
        User savedUser = userRepository.save(user);

        emailService.sendVerificationEmail(savedUser);
        return savedUser;
    }

    private String generateToken(){
        return UUID.randomUUID().toString();
    }
}
