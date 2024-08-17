package com.example.my_spring_boot_app.controller;

import com.example.my_spring_boot_app.model.VerificationRequest;
import com.example.my_spring_boot_app.repository.UserRepository;
import com.example.my_spring_boot_app.services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class VerificationController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerificationRequest request){
        String token = request.getToken();
        System.out.println("THE REQUESTED TOKEN IS: "+ request.getToken());
        User user = userRepository.findByVerificationToken(token);
        if(user!=null){
            if(user.isTokenExpired()) {
                userRepository.delete(user);
                return ResponseEntity.badRequest().body("Token expired. User removed.");
            }
            else{
                user.setVerificationToken(null);
                user.setTokenExpiration(null);
                userRepository.save(user);
                return ResponseEntity.ok("User verified successfully");
            }
        }
        else{
            return ResponseEntity.badRequest().body("Invalid Token");
        }
    }
}
