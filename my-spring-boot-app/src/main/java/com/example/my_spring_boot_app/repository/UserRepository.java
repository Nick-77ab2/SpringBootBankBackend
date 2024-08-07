package com.example.my_spring_boot_app.repository;

import com.example.my_spring_boot_app.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
