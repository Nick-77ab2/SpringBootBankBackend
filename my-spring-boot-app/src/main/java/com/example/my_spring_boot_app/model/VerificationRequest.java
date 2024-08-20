package com.example.my_spring_boot_app.model;

public class VerificationRequest {
    private String token;
    private String password;

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password=password;
    }
}
