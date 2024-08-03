package com.example.my_spring_boot_app.repository;

import com.example.my_spring_boot_app.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository <Message, Long> {
}