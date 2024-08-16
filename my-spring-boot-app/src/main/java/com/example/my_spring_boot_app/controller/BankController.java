package com.example.my_spring_boot_app.controller;

import com.example.my_spring_boot_app.model.BalanceRequest;
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
    public ResponseEntity<BigDecimal> transfer(@RequestBody BalanceRequest balanceRequest){
        try {
            double amount = balanceRequest.getAmount();
            String fromUserId=balanceRequest.getFromUser();
            String toUserId = balanceRequest.getToUser();
            bankingService.processTransaction(fromUserId, toUserId, amount);
            BigDecimal balance = bankingService.getBalance(fromUserId);
            return ResponseEntity.ok(balance);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
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
    public ResponseEntity<BigDecimal> addBalance(@PathVariable String username, @RequestBody BalanceRequest balanceRequest) {
        try {
            System.out.println("Received request body: " + balanceRequest);
            double amount = balanceRequest.getAmount();
            System.out.println("Amount to add: " + amount); // Log the amount
            bankingService.addBalance(username, amount);
            BigDecimal balance = bankingService.getBalance(username);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/removebalance/{username}")
    public ResponseEntity<BigDecimal> removeBalance(@PathVariable String username, @RequestBody BalanceRequest balanceRequest) {
        try {
            System.out.println("Received request body: " + balanceRequest);
            double amount = balanceRequest.getAmount();
            bankingService.removeBalance(username, amount);
            BigDecimal balance = bankingService.getBalance(username);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
