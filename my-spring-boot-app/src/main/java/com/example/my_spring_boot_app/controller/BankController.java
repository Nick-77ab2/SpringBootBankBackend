package com.example.my_spring_boot_app.controller;

import com.example.my_spring_boot_app.services.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankingService bankingService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long fromUserId, @RequestParam Long toUserId, @RequestParam double amount){
        try {
            bankingService.processTransaction(fromUserId, toUserId, amount);
            return ResponseEntity.ok("Transfer successful");
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/balance/{username}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String username){
        try {
            BigDecimal balance = bankingService.getBalance(username);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/addbalance/{username}")
    public ResponseEntity<BigDecimal> addBalance(@PathVariable String username, @PathVariable double amount) {
        try {
            bankingService.addBalance(username, amount);
            BigDecimal balance = bankingService.getBalance(username);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/removebalance/{username}")
    public ResponseEntity<BigDecimal> removeBalance(@PathVariable String username, @PathVariable double amount) {
        try {
            bankingService.removeBalance(username, amount);
            BigDecimal balance = bankingService.getBalance(username);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
