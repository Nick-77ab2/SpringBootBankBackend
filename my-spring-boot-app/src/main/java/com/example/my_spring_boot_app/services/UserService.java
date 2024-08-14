package com.example.my_spring_boot_app.services;

import com.example.my_spring_boot_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user){
        user.setPassword((passwordEncoder.encode(user.getPassword())));
        user.setAccount(5);
        return userRepository.save(user);
    }
}
