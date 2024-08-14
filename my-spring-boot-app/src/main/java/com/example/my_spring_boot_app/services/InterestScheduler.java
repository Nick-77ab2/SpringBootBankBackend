package com.example.my_spring_boot_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InterestScheduler {

    @Autowired
    private BankingService bankingService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void runInterestCalculation(){
        bankingService.calculateInterest();
    }

}
