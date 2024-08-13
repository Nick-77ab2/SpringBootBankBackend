package com.example.my_spring_boot_app.controller;

import com.example.my_spring_boot_app.repository.UserRepository;
import com.example.my_spring_boot_app.security.AuthRequest;
import com.example.my_spring_boot_app.security.JwtUtil;
import com.example.my_spring_boot_app.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.example.my_spring_boot_app.security.CustomUserDetailsService;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/authentication")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            final User user = userDetailsService.loadRegularUserByUsername(authRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername(),user);
            return ResponseEntity.ok(jwt);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
