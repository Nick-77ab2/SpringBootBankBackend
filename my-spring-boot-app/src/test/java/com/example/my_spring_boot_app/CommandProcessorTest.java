package com.example.my_spring_boot_app;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
    public static final String INPUT1 = "create checking 12345678 1.0";
    public static final String INPUT2 = "create savings 12345679 1.0";
    public static final String INPUT3 = "create cd 12345688 1.0 1000";
    public static final String DINPUT1 = "deposit 12345678 678";
    public static final String DINPUT2 = "deposit 12345679 678";
    public static final String DINPUT3 = "deposit 12345678 99";
    public static final String DINPUT4 = "deposit 12345679 99";
    public static final String TINPUT1 = "pass 1";
    public static final String TINPUT5 = "pass 5";
    public static final String WINPUT1 = "withdraw 12345678 200";
    public static final String WINPUT2 = "withdraw 12345679 200";

    Bank bank;
    CommandProcessor commandProcessor;
    CommandValidator commandValidator;
    int timePassed;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        timePassed = PassTime.getCurrentTime();
        commandProcessor = new CommandProcessor(bank);
        commandValidator = new CommandValidator(bank);
    }

    @Test
    void pass_time_one_month() {
        commandProcessor.calculateProcessor(INPUT1);
        commandProcessor.calculateProcessor(TINPUT1);
        assertEquals(timePassed + 1, PassTime.getCurrentTime());
    }

    @Test
    void check_create_checking() {
        commandProcessor.calculateProcessor(INPUT1);
        assertEquals("checking", bank.getAccounts().get("12345678").getAccountType());
        assertEquals(1.0f, bank.getAccounts().get("12345678").getApr());
        assertEquals("12345678", bank.getAccounts().get("12345678").getId());
    }

    @Test
    void check_create_savings() {
        commandProcessor.calculateProcessor(INPUT2);
        assertEquals("savings", bank.getAccounts().get("12345679").getAccountType());
        assertEquals(1.0f, bank.getAccounts().get("12345679").getApr());
        assertEquals("12345679", bank.getAccounts().get("12345679").getId());
    }

    @Test
    void check_create_cd() {
        commandProcessor.calculateProcessor(INPUT3);
        assertEquals("cd", bank.getAccounts().get("12345688").getAccountType());
        assertEquals(1.0f, bank.getAccounts().get("12345688").getApr());
        assertEquals("12345688", bank.getAccounts().get("12345688").getId());
        assertEquals(new BigDecimal("1000.00"), bank.getAccounts().get("12345688").getBalance());
    }

    @Test
    void check_deposit_checking() {
        commandProcessor.calculateProcessor(INPUT1);
        commandProcessor.calculateProcessor(DINPUT1);
        assertEquals(new BigDecimal("678.00"), bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void check_deposit_savings() {
        commandProcessor.calculateProcessor(INPUT2);
        commandProcessor.calculateProcessor(DINPUT2);
        assertEquals(new BigDecimal("678.00"), bank.getAccounts().get("12345679").getBalance());
    }

    @Test
    void check_multi_deposit_checking() {
        commandProcessor.calculateProcessor(INPUT1);
        commandProcessor.calculateProcessor(DINPUT1);
        commandProcessor.calculateProcessor(DINPUT1);
        assertEquals(new BigDecimal("1356.00"), bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void check_multi_deposit_savings() {
        commandProcessor.calculateProcessor(INPUT2);
        commandProcessor.calculateProcessor(DINPUT2);
        commandProcessor.calculateProcessor(DINPUT2);
        assertEquals(new BigDecimal("1356.00"), bank.getAccounts().get("12345679").getBalance());
    }

    @Test
    void pass_time_one_month_check_apr_multiple_accounts() {
        commandProcessor.calculateProcessor(INPUT1);
        commandProcessor.calculateProcessor(INPUT2);
        commandProcessor.calculateProcessor(INPUT3);
        commandProcessor.calculateProcessor(DINPUT1);
        commandProcessor.calculateProcessor(DINPUT2);
        commandProcessor.calculateProcessor(TINPUT1);
        assertEquals(timePassed + 1, PassTime.getCurrentTime());
        assertEquals(new BigDecimal("678.57"), bank.getAccounts().get("12345678").getBalance());
        assertEquals(new BigDecimal("678.57"), bank.getAccounts().get("12345679").getBalance());
        assertEquals(new BigDecimal("1003.34"), bank.getAccounts().get("12345688").getBalance());
    }

    @Test
    void pass_time_one_month_check_apr_checking() {
        commandProcessor.calculateProcessor(INPUT1);
        commandProcessor.calculateProcessor(DINPUT1);
        commandProcessor.calculateProcessor(TINPUT1);
        assertEquals(timePassed + 1, PassTime.getCurrentTime());
        assertEquals(new BigDecimal("678.57"), bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void pass_time_one_month_check_apr_savings() {
        commandProcessor.calculateProcessor(INPUT2);
        commandProcessor.calculateProcessor(DINPUT2);
        commandProcessor.calculateProcessor(TINPUT1);
        assertEquals(timePassed + 1, PassTime.getCurrentTime());
        assertEquals(new BigDecimal("678.57"), bank.getAccounts().get("12345679").getBalance());
    }

    @Test
    void pass_time_one_month_check_apr_cd() {
        commandProcessor.calculateProcessor(INPUT3);
        commandProcessor.calculateProcessor(TINPUT1);
        assertEquals(timePassed + 1, PassTime.getCurrentTime());
        assertEquals(new BigDecimal("1003.34"), bank.getAccounts().get("12345688").getBalance());
    }

    @Test
    void checking_account_actually_loses_balance_if_below_hundred_when_time_passed() {
        commandProcessor.calculateProcessor(INPUT1);
        commandProcessor.calculateProcessor(DINPUT3);
        commandProcessor.calculateProcessor(TINPUT1);
        assertEquals(new BigDecimal("74.07"), bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void savings_account_actually_loses_balance_if_below_hundred_when_time_passed() {
        commandProcessor.calculateProcessor(INPUT2);
        commandProcessor.calculateProcessor(DINPUT4);
        commandProcessor.calculateProcessor(TINPUT1);
        assertEquals(new BigDecimal("74.07"), bank.getAccounts().get("12345679").getBalance());
    }

    @Test
    void checking_account_balance_zero_or_below_removed_when_time_passed() {
        commandProcessor.calculateProcessor("create checking 12345789 1.0");
        commandProcessor.calculateProcessor("deposit 12345789 25");
        commandProcessor.calculateProcessor(TINPUT1);
        assertNull(bank.getAccounts().get("12345789"));
    }

    @Test
    void savings_account_balance_zero_or_below_removed_when_time_passed() {
        commandProcessor.calculateProcessor("create savings 12345798 1.0");
        commandProcessor.calculateProcessor("create savings 12345799 1.0");
        commandProcessor.calculateProcessor("deposit 12345798 25");
        commandProcessor.calculateProcessor("deposit 12345799 101");
        commandProcessor.calculateProcessor(TINPUT1);
        assertNull(bank.getAccounts().get("12345798"));
    }

    @Test
    void pass_time_five_months_check_apr_cd() {
        commandProcessor.calculateProcessor(INPUT3);
        commandProcessor.calculateProcessor(TINPUT5);
        assertEquals(timePassed + 5, PassTime.getCurrentTime());
        assertEquals(new BigDecimal("1016.82"), bank.getAccounts().get("12345688").getBalance());
    }

    @Test
    void check_withdraw_checking() {
        commandProcessor.calculateProcessor(INPUT1);
        commandProcessor.calculateProcessor(DINPUT1);
        commandProcessor.calculateProcessor(WINPUT1);
        assertEquals(new BigDecimal("478.00"), bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void check_withdraw_savings() {
        commandProcessor.calculateProcessor(INPUT2);
        commandProcessor.calculateProcessor(DINPUT2);
        commandProcessor.calculateProcessor(WINPUT2);
        assertEquals(new BigDecimal("478.00"), bank.getAccounts().get("12345679").getBalance());
    }

    @Test
    void check_withdraw_from_savings_sets_correct_month() {
        commandProcessor.calculateProcessor(INPUT2);
        commandProcessor.calculateProcessor(DINPUT2);
        commandProcessor.calculateProcessor(WINPUT2);
        Savings checkSavings = (Savings) bank.getAccounts().get("12345679");
        assertEquals(new BigDecimal("478.00"), bank.getAccounts().get("12345679").getBalance());
        assertEquals(com.example.my_spring_boot_app.PassTime.getCurrentTime(), checkSavings.getLastWithdrawMonth());
    }

    @Test
    void withdraw_one_month_then_another() {
        commandProcessor.calculateProcessor((INPUT2));
        commandProcessor.calculateProcessor(DINPUT2);
        commandProcessor.calculateProcessor(WINPUT2);
        assertEquals(new BigDecimal("478.00"), bank.getAccounts().get("12345679").getBalance());
        commandProcessor.calculateProcessor(TINPUT1);
        commandProcessor.calculateProcessor(WINPUT2);
        assertEquals(new BigDecimal("278.40"), bank.getAccounts().get("12345679").getBalance());
    }

    @Test
    void Withdraw_greater_than_account_size() {
        commandProcessor.calculateProcessor((INPUT2));
        commandProcessor.calculateProcessor(DINPUT4);
        commandProcessor.calculateProcessor(WINPUT2);
        assertEquals(new BigDecimal("0.00"), bank.getAccounts().get("12345679").getBalance());
    }
}