package com.example.my_spring_boot_app;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

//@Table(name = "user_account")
@Embeddable
public class Account implements Serializable {
    protected BigDecimal balance = BigDecimal.valueOf(0);
    protected String type;
    private String accid;

    private float apr;

    protected Account(){

    }
    protected Account(String accid, float apr) {
        this.accid = accid;
        this.apr = apr;
    }

    protected Account(String accid, float apr, double deposit) {
        this.accid = accid;
        this.apr = apr;
        BigDecimal value = BigDecimal.valueOf(deposit);
        this.balance = balance.add(value);
    }

    public String getAccountType() {
        return type;
    }

    public String getAccid() {
        return accid;
    }

    public float getApr() {
        return apr;
    }

    public void addBalance(double value) {
        BigDecimal add = BigDecimal.valueOf(value);
        System.out.println("THE PREVIOUS BALANCE IS: " + balance + " AND THE AMOUNT TO ADD IS: " + add + "THE ORIGINAL AMOUNT TO ADD IS: " + value);
        balance = balance.add(add);
        System.out.println("THE NEW BALANCE IS: " + balance);
    }
    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    public void removeBalance(double value) {
        BigDecimal remove = BigDecimal.valueOf(value);
        System.out.println("THE PREVIOUS BALANCE IS: " + balance + " AND THE AMOUNT TO ADD IS: " + remove);
        balance = balance.subtract(remove);
        System.out.println("THE NEW BALANCE IS: " + balance);
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
