package com.example.my_spring_boot_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {
    @SuppressWarnings("unused")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    @SuppressWarnings("unused")
    public Message() {
    }
    @SuppressWarnings("unused")
    public Message(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @SuppressWarnings("unused")
    public String getContent() {
        return content;
    }
    @SuppressWarnings("unused")
    public void setContent(String content) {
        this.content = content;
    }
}
