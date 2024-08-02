package com.example.my_spring_boot_app;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {
    protected BigDecimal balance = BigDecimal.valueOf(0);
    protected String type;
    private String id;
    private float apr;

    protected Account(String id, float apr) {
        this.id = id;
        this.apr = apr;
    }

    protected Account(String id, float apr, double deposit) {
        this.id = id;
        this.apr = apr;
        BigDecimal value = BigDecimal.valueOf(deposit);
        this.balance = balance.add(value);
    }

    public String getAccountType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public float getApr() {
        return apr;
    }

    public void addBalance(String value) {
        BigDecimal add = new BigDecimal(value);
        balance = balance.add(add);
    }

    public void removeBalance(String value) {
        BigDecimal remove = new BigDecimal(value);
        if (getBalance().compareTo(BigDecimal.ZERO) == 0) {
            balance = getBalance();
        } else if ((getBalance().subtract(remove)).compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.subtract(remove);
        } else if ((getBalance().subtract(remove)).compareTo(BigDecimal.ZERO) <= 0) {
            balance = balance.subtract(balance);
        }
    }

    public BigDecimal getBalance() {
        balance = balance.setScale(2, RoundingMode.CEILING);
        return balance;
    }

    public void calculateAPR() {
        float calculateAPR = apr;
        calculateAPR /= 100;
        calculateAPR /= 12;
        BigDecimal quickBalance = balance;
        quickBalance = (quickBalance.multiply(BigDecimal.valueOf(calculateAPR)));
        balance = balance.add(quickBalance);
    }
}
