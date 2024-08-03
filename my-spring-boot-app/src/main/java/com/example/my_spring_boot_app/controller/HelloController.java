package com.example.my_spring_boot_app.controller;

import com.example.my_spring_boot_app.model.Message;
import com.example.my_spring_boot_app.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    @SuppressWarnings("unused")
    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/hello")
    public Message saveMessage(@RequestBody Message message) {
        return messageRepository.save(message);
    }

    @GetMapping("/hello")
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }
}