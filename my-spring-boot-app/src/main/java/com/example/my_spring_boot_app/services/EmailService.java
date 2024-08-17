package com.example.my_spring_boot_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nickstestbank@gmail.com");
        message.setTo(user.getUsername());
        message.setSubject("Email Verification");
        message.setText("Click the link to verify your account " + "http://localhost:3000/verify?token=" + user.getVerificationToken());
        mailSender.send(message);
    }
}
