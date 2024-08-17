package com.example.my_spring_boot_app.model;

public class VerificationRequest {
    private String token;

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }
}
