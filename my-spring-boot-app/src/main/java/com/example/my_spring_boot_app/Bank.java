package com.example.my_spring_boot_app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
@SuppressWarnings("LeakingThisInConstructor")
public class Bank {
    private final Map<String, Account> accounts;

    Bank() {
        accounts = new HashMap<>();
        PassTime.registerObserver(this);
    }

    protected void addAccount(String id, Account account) {
        accounts.put(id, account);
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void deposit(String id, String amount) {
        accounts.get(id).addBalance(amount);
    }

    public void withdraw(String id, String removeAmount) {
        accounts.get(id).removeBalance(removeAmount);
    }

    public boolean accountExistsByID(String id) {
        return accounts.get(id) != null;
    }

    protected void passTime(int time) {
        for (int currentMonth = 0; currentMonth < time; currentMonth += 1) {
            Iterator<Map.Entry<String, Account>> it = accounts.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Account> account = it.next();
                runMonthChecklist(it, account);
            }
        }
    }

    private void runMonthChecklist(Iterator<Map.Entry<String, Account>> it, Map.Entry<String, Account> account) {
        if (account.getValue().getBalance().compareTo(BigDecimal.valueOf(100)) <= 0) {
            account.getValue().removeBalance("25");
        }
        if (account.getValue().getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            it.remove();
        } else {
            account.getValue().calculateAPR();
        }
    }
}
