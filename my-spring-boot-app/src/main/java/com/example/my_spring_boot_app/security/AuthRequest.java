package com.example.my_spring_boot_app.security;

public class AuthRequest {
    private String username;
    private String password;

    // Getters and setters
    public String getUsername(){
        return username;
    }

    protected void setUsername(String username){
        this.username=username;
    }

    public String getPassword(){
        return password;
    }

    protected void setPassword(String password){
        this.password=password;
    }
}
