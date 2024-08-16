package com.example.my_spring_boot_app.model;

public class BalanceRequest {
    private double amount;
    private String fromUser;
    private String toUser;

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount=amount;
    }

    public String getFromUser(){
        return fromUser;
    }

    protected void setFromUser(String fromUser){
        this.fromUser=fromUser;
    }

    public String getToUser(){
        return toUser;
    }

    protected void setToUser(String toUser){
        this.toUser=toUser;
    }

    @Override
    public String toString() {
        return "BalanceRequest{amount=" + amount + "}";
    }
}
