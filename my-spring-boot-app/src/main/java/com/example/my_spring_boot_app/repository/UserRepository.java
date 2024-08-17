package com.example.my_spring_boot_app.repository;

import com.example.my_spring_boot_app.services.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByVerificationToken(String token);
}
