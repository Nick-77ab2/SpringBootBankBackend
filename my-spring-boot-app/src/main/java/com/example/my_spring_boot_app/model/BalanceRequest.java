package com.example.my_spring_boot_app.model;

public class BalanceRequest {
    private double amount;

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount=amount;
    }

    @Override
    public String toString() {
        return "BalanceRequest{amount=" + amount + "}";
    }
}
