package com.example.my_spring_boot_app.controller;

import com.example.my_spring_boot_app.model.UsernameRequest;
import com.example.my_spring_boot_app.model.VerificationRequest;
import com.example.my_spring_boot_app.repository.UserRepository;
import com.example.my_spring_boot_app.services.CustomUserDetailsService;
import com.example.my_spring_boot_app.services.EmailService;
import com.example.my_spring_boot_app.services.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PasswordResetController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    private String generateToken(){
        return UUID.randomUUID().toString();
    }

    @PostMapping("passwordReset")
    public ResponseEntity<?> sendReset(@RequestBody UsernameRequest usernameRequest) {
        try {
            String token = generateToken();
            User user = userDetailsService.loadRegularUserByUsername(usernameRequest.getUsername());
            System.out.println("THE USER IS: " + user.getUsername());
            user.setVerificationToken(token);
            user.setTokenExpiration(LocalDateTime.now().plusMinutes(10));
            userRepository.save(user);
            emailService.sendResetEmail(user);

            return ResponseEntity.ok("Successfully sent email");
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("User doesn't exist");
        }
    }

    @PostMapping("/passwordReset/reset")
    public ResponseEntity<?> reset(@RequestBody VerificationRequest request){
        String token = request.getToken();
        System.out.println("THE REQUESTED TOKEN IS: "+ request.getToken());
        User user = userRepository.findByVerificationToken(token);
        if(user!=null){
            if(user.isTokenExpired()) {
                return ResponseEntity.badRequest().body("Token expired. Start from the beginning.");
            }
            else{
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setVerificationToken(null);
                user.setTokenExpiration(null);
                userRepository.save(user);
                return ResponseEntity.ok("Password updated");
            }
        }
        else{
            return ResponseEntity.badRequest().body("Something went horribly wrong");
        }
    }


}
