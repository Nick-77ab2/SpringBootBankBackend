package com.example.my_spring_boot_app;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
    public static final String ID = "10454598";
    public static final String ID2 = "15679860";
    public static final String ID3 = "43520554";
    public static final float APR = 0.05f;
    public static final float APR2 = 0.07f;
    public static final float APR3 = 0.09f;
    public static final String ADD_AMOUNT = "50";
    public static final String REMOVE_AMOUNT = "25";
    Account checking;
    Account cd;
    Account savings;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(ID, APR);
        savings = new Savings(ID2, APR2);
        cd = new CD(ID3, APR3, 1001);
        bank.addAccount(checking.getAccid(), checking);
        bank.addAccount(savings.getAccid(), savings);
        bank.addAccount(cd.getAccid(), cd);
    }

    @Test
    void add_account_to_bank() {
        assertEquals(ID, bank.getAccounts().get(ID).getAccid());
        assertEquals(APR, bank.getAccounts().get(ID).getApr());
    }

    @Test
    void deposit_to_checking_account_once_and_twice() {
        bank.deposit(ID, ADD_AMOUNT);
        assertEquals(new BigDecimal("50.00"), bank.getAccounts().get(ID).getBalance());
        bank.deposit(ID, ADD_AMOUNT);
        assertEquals(new BigDecimal("100.00"), bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void deposit_to_savings_account_once_and_twice() {
        bank.deposit(ID2, ADD_AMOUNT);
        assertEquals(new BigDecimal("50.00"), bank.getAccounts().get(ID2).getBalance());
        bank.deposit(ID2, ADD_AMOUNT);
        assertEquals(new BigDecimal("100.00"), bank.getAccounts().get(ID2).getBalance());

    }

    @Test
    void deposit_to_cd_account_once_and_twice() {
        bank.deposit(ID3, ADD_AMOUNT);
        assertEquals(new BigDecimal("1001.00"), bank.getAccounts().get(ID3).getBalance());
        bank.deposit(ID3, ADD_AMOUNT);
        assertEquals(new BigDecimal("1001.00"), bank.getAccounts().get(ID3).getBalance());

    }

    @Test
    void withdraw_from_checking_account_once_and_twice() {
        bank.deposit(ID, ADD_AMOUNT);
        bank.withdraw(ID, REMOVE_AMOUNT);
        assertEquals(new BigDecimal("25.00"), bank.getAccounts().get(ID).getBalance());
        bank.withdraw(ID, REMOVE_AMOUNT);
        assertEquals(new BigDecimal("0.00"), bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void withdraw_from_savings_account_once_and_twice() {
        bank.deposit(ID2, ADD_AMOUNT);
        bank.withdraw(ID2, REMOVE_AMOUNT);
        assertEquals(new BigDecimal("25.00"), bank.getAccounts().get(ID2).getBalance());
        bank.withdraw(ID2, REMOVE_AMOUNT);
        assertEquals(new BigDecimal("0.00"), bank.getAccounts().get(ID2).getBalance());
    }

    @Test
    void withdraw_from_cd_account_once_and_twice() {
        bank.withdraw(ID3, REMOVE_AMOUNT);
        assertEquals(new BigDecimal("976.00"), bank.getAccounts().get(ID3).getBalance());
        bank.withdraw(ID3, REMOVE_AMOUNT);
        assertEquals(new BigDecimal("951.00"), bank.getAccounts().get(ID3).getBalance());
    }
}
