package com.example.my_spring_boot_app;

import java.math.BigDecimal;

public class Savings extends Account {
    private int lastWithdrawMonth = -1;

    public Savings(String id, float apr) {
        super(id, apr);
        type = "savings";
    }

    public int getLastWithdrawMonth() {
        return lastWithdrawMonth;
    }

    public void setLastWithdrawMonth() {
        lastWithdrawMonth = com.example.my_spring_boot_app.PassTime.getCurrentTime();
    }

}
