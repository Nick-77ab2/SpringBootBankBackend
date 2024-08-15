package com.example.my_spring_boot_app.services;


import com.example.my_spring_boot_app.Account;
import com.example.my_spring_boot_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankingService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void processTransaction(Long fromUserId, Long toUserId, double amount){
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Account fromAccount = fromUser.getAccount();
        Account toAccount = toUser.getAccount();
        BigDecimal amt = new BigDecimal(amount);
        if (fromAccount.getBalance().subtract(amt).compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromAccount.removeBalance(amount);
        toAccount.addBalance(amount);

        userRepository.save(fromUser);
        userRepository.save(toUser);

    }

    @Transactional
    public void calculateInterest(){
        Iterable<User> users = userRepository.findAll();
        for (User user: users){
            Account account = user.getAccount();
            BigDecimal monthlyRate = BigDecimal.valueOf(Math.pow(1 + (account.getApr() / 100), 1.0 / 12) - 1);
            BigDecimal newBalanceAddition = account.getBalance().multiply(BigDecimal.ONE.add(monthlyRate));
            account.setBalance(newBalanceAddition);
            userRepository.save(user);
        }
    }

    public BigDecimal getBalance(String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("Invalid username");
        }
        System.out.println("Fetched user: " + user);
        Account account = user.getAccount();
        if(account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        System.out.println("Fetched account: " + account); // Log the account
        return account.getBalance();
    }

    public void addBalance(String username, double amount){
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("Invalid username");
        }
        System.out.println("Fetched user: " + user);
        Account account = user.getAccount();
        if(account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        System.out.println("Fetched account: " + account); // Log the account
        account.addBalance(amount);
        userRepository.save(user);
    }

    public void removeBalance(String username, double amount){
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("Invalid username");
        }
        System.out.println("Fetched user: " + user);
        Account account = user.getAccount();
        if(account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        System.out.println("Fetched account: " + account); // Log the account
        account.removeBalance(amount);
        userRepository.save(user);
    }
}
