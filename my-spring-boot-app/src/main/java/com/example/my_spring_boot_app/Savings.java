package com.example.my_spring_boot_app;

import java.math.BigDecimal;

public class Savings extends Account {
    private int lastWithdrawMonth = -1;

    public Savings(String id, float apr) {
        super(id, apr);
        type = "savings";
    }

    @Override
    public void addBalance(String value) {
        BigDecimal add = new BigDecimal(value);
        balance = balance.add(add);
    }

    @Override
    public void removeBalance(String value) {
        BigDecimal remove = new BigDecimal(value);
        if (getBalance().compareTo(BigDecimal.ZERO) == 0) {
            balance = getBalance();
        } else if ((getBalance().subtract(remove)).compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.subtract(remove);
            setLastWithdrawMonth();
        } else if ((getBalance().subtract(remove)).compareTo(BigDecimal.ZERO) <= 0) {
            balance = balance.subtract(balance);
            setLastWithdrawMonth();
        }
    }

    public int getLastWithdrawMonth() {
        return lastWithdrawMonth;
    }

    public void setLastWithdrawMonth() {
        lastWithdrawMonth = com.example.my_spring_boot_app.PassTime.getCurrentTime();
    }

}
