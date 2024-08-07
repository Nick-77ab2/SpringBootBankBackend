package com.example.my_spring_boot_app.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;

    // Getters and setters
    public Long getId(){
        return id;
    }
    protected void setId(Long id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }
    protected void setUsername(){
        this.username=username;
    }

    public String getPassword(){
        return password;
    }

    protected void setPassword(String password){
        this.password=password;
    }

    public String getName(){
        return name;
    }

    protected void setName(String name){
        this.name=name;
    }
}

