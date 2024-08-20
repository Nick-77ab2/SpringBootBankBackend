package com.example.my_spring_boot_app.services;

import com.example.my_spring_boot_app.Account;
import com.example.my_spring_boot_app.Savings;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "app_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String verificationToken;
    private LocalDateTime tokenExpiration;
    @Embedded
    private Account account;

    // Getters and setters
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername() {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTokenExpired(){
        return LocalDateTime.now().isAfter(tokenExpiration);
    }

    protected void setAccount(float apr){
        this.account = new Savings(this.getUsername(), apr);
    }
    public Account getAccount(){
        return this.account;
    }

    //UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken){
        this.verificationToken=verificationToken;
    }

    public LocalDateTime getTokenExpiration(){
        return tokenExpiration;
    }

    public void setTokenExpiration(LocalDateTime tokenExpiration){
        this.tokenExpiration=tokenExpiration;
    }
}

