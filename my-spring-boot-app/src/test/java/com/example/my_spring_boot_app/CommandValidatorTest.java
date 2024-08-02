package com.example.my_spring_boot_app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
    CommandValidator commandValidator;
    CommandProcessor commandProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    void valid_create_command() {
        boolean actual = commandValidator.calculateValidator("Create savings 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void invalid_case_create_command() {
        boolean actual = commandValidator.calculateValidator("CrEaTe savings 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void invalid_length_create_command() {
        boolean actual = commandValidator.calculateValidator("Create savings 12345678 0.6 ooga");
        boolean actual1 = commandValidator.calculateValidator("Create savings 12345678");
        boolean actual2 = commandValidator.calculateValidator("Create savings");
        boolean actual3 = commandValidator.calculateValidator("Create");
        assertFalse(actual);
        assertFalse(actual1);
        assertFalse(actual2);
        assertFalse(actual3);

    }

    @Test
    void invalid_length_deposit_command() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("deposit 12345678 100 aba");
        boolean actual1 = commandValidator.calculateValidator("deposit 12345678");
        boolean actual2 = commandValidator.calculateValidator("deposit");
        assertFalse(actual);
        assertFalse(actual1);
        assertFalse(actual2);
    }

    @Test
    void invalid_length_pass_time_command() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("pass 1 aba");
        boolean actual1 = commandValidator.calculateValidator("pass");
        assertFalse(actual);
        assertFalse(actual1);
    }

    @Test
    void invalid_duplicate_id_create_command() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("Create savings 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void invalid_id_length_create_command() {
        boolean actual = commandValidator.calculateValidator("Create savings 1234567 0.6");
        boolean actual1 = commandValidator.calculateValidator("Create savings 123456789 0.6");
        assertFalse(actual);
        assertFalse(actual1);
    }

    @Test
    void invalid_id_type_create_command() {
        boolean actual = commandValidator.calculateValidator("Create savings 1a3b5c7d 0.6");
        boolean actual1 = commandValidator.calculateValidator("Create savings 12345678 0.6");
        assertFalse(actual);
        assertTrue(actual1);
    }

    @Test
    void invalid_size_apr() {
        boolean actual = commandValidator.calculateValidator("Create savings 12345678 11");
        boolean actual1 = commandValidator.calculateValidator("Create savings 12345678 10");
        assertFalse(actual);
        assertTrue(actual1);
    }

    @Test
    void create_account_type() {
        boolean actual = commandValidator.calculateValidator("Create savings 12345678 0.6");
        boolean actual1 = commandValidator.calculateValidator("Create checking 22345678 0.6");
        boolean actual2 = commandValidator.calculateValidator("Create cd 32345678 0.6 2000");
        assertTrue(actual);
        assertTrue(actual1);
        assertTrue(actual2);
    }

    @Test
    void check_creation_invalid_account_type() {
        boolean actual = commandValidator.calculateValidator("Create hevver 42345678 0.6");
        assertFalse(actual);
    }

    @Test
    void check_creation_value() {
        boolean actual = commandValidator.calculateValidator("Create savings 12345678 0.6");
        boolean actual1 = commandValidator.calculateValidator("Create checking 22345678 0.6");
        assertTrue(actual);
        assertTrue(actual1);
    }

    @Test
    void check_account_checking_savings_deposit() {
        boolean actual = commandValidator.calculateValidator("Create savings 12345678 0.6 342");
        boolean actual1 = commandValidator.calculateValidator("Create checking 22345678 0.6 6234");
        assertFalse(actual);
        assertFalse(actual1);
    }

    @Test
    void check_account_with_deposit() {
        boolean actual = commandValidator.calculateValidator("Create cd 12345678 0.6");
        boolean actual1 = commandValidator.calculateValidator("Create cd 12345678 0.6 999");
        boolean actual2 = commandValidator.calculateValidator("Create cd 22345678 0.6 1000");
        boolean actual3 = commandValidator.calculateValidator("Create cd 22345678 0.6 1001");
        boolean actual4 = commandValidator.calculateValidator("Create cd 22345678 0.6 9999");
        boolean actual5 = commandValidator.calculateValidator("Create cd 22345678 0.6 10000");
        boolean actual6 = commandValidator.calculateValidator("Create cd 22345678 0.6 10001");
        assertFalse(actual);
        assertFalse(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
        assertTrue(actual4);
        assertTrue(actual5);
        assertFalse(actual6);
    }

    @Test
    void check_apr_is_float() {
        boolean actual = commandValidator.calculateValidator("Create checking 12345678 0.6");
        boolean actual1 = commandValidator.calculateValidator("Create checking 22345678 2fdf");
        assertTrue(actual);
        assertFalse(actual1);
    }

    @Test
    void check_deposit_case() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("Deposit 12345678 500");
        boolean actual1 = commandValidator.calculateValidator("DePosit 12345678 500");
        assertTrue(actual);
        assertTrue(actual1);
    }

    @Test
    void check_deposit_ids() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("Deposit 12345678 500");
        boolean actual1 = commandValidator.calculateValidator("Deposit 12345679 500");
        boolean actual2 = commandValidator.calculateValidator("Deposit 1234567 500");
        assertTrue(actual);
        assertFalse(actual1);
        assertFalse(actual2);
    }

    @Test
    void check_deposit_id_type() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        Account checking = new Checking("12345679", 0.6f);
        bank.addAccount(checking.getId(), checking);
        Account cd = new CD("12345689", 0.6f, 1000);
        bank.addAccount(cd.getId(), cd);
        boolean actual = commandValidator.calculateValidator("Deposit 12345678 500");
        boolean actual1 = commandValidator.calculateValidator("Deposit 12345679 500");
        boolean actual2 = commandValidator.calculateValidator("Deposit 12345689 500");
        assertTrue(actual);
        assertTrue(actual1);
        assertFalse(actual2);
    }

    @Test
    void check_deposit_large_sum() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        Account checking = new Checking("12345679", 0.6f);
        bank.addAccount(checking.getId(), checking);
        boolean actual = commandValidator.calculateValidator("Deposit 12345678 2499");
        boolean actual1 = commandValidator.calculateValidator("Deposit 12345678 2500");
        boolean actual2 = commandValidator.calculateValidator("Deposit 12345678 2501");
        boolean actual3 = commandValidator.calculateValidator("Deposit 12345679 999");
        boolean actual4 = commandValidator.calculateValidator("Deposit 12345679 1000");
        boolean actual5 = commandValidator.calculateValidator("Deposit 12345679 1001");
        assertTrue(actual);
        assertTrue(actual1);
        assertFalse(actual2);
        assertTrue(actual3);
        assertTrue(actual4);
        assertFalse(actual5);
    }

    @Test
    void check_deposit_negatives_and_zero() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("Deposit 12345678 -1");
        boolean actual1 = commandValidator.calculateValidator("Deposit 12345678 0");
        assertFalse(actual);
        assertTrue(actual1);
    }

    @Test
    void check_deposit_null() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("Deposit 12345678");
        boolean actual1 = commandValidator.calculateValidator("Deposit 1234567");
        assertFalse(actual);
        assertFalse(actual1);
    }

    @Test
    void check_pass_time_input() {
        boolean actual = commandValidator.calculateValidator("Pass 1");
        boolean actual1 = commandValidator.calculateValidator("Pyass 1");
        boolean actual2 = commandValidator.calculateValidator("pass oogabooga");
        assertTrue(actual);
        assertFalse(actual1);
        assertFalse(actual2);
    }

    @Test
    void pass_time_input_bounds() {
        boolean actual = commandValidator.calculateValidator("Pass 0");
        boolean actual1 = commandValidator.calculateValidator("Pass 1");
        boolean actual2 = commandValidator.calculateValidator("Pass 2");
        boolean actual3 = commandValidator.calculateValidator("Pass 60");
        boolean actual4 = commandValidator.calculateValidator("Pass 61");
        assertFalse(actual);
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
        assertFalse(actual4);
    }

    @Test
    void valid_withdraw_commands() {
        Account savings = new Savings("12345678", 0.6f);
        Account checking = new Checking("12345679", 0.6f);
        Account cd = new CD("12345688", 0.6f, 1000);
        bank.addAccount(savings.getId(), savings);
        bank.addAccount(checking.getId(), checking);
        bank.addAccount(cd.getId(), cd);
        boolean actual = commandValidator.calculateValidator("withdraw 12345678 1");
        boolean actual1 = commandValidator.calculateValidator("withdraw 12345679 1");
        boolean actual2 = commandValidator.calculateValidator("withdraw 12345688 1");
        assertTrue(actual);
        assertTrue(actual1);
        assertFalse(actual2);

    }

    @Test
    void invalid_withdraw_command_invalid_withdraw() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("withdraw 12345678 ooga");
        assertFalse(actual);
    }

    @Test
    void invalid_withdraw_command_invalid_lengths() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("withdraw 12345678 1 ooga");
        boolean actual1 = commandValidator.calculateValidator("withdraw 12345678");
        assertFalse(actual);
        assertFalse(actual1);
    }

    @Test
    void bounding_amounts_for_savings() {
        Account savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("withdraw 12345678 -1");
        boolean actual1 = commandValidator.calculateValidator("withdraw 12345678 0");
        boolean actual2 = commandValidator.calculateValidator("withdraw 12345678 1");
        boolean actual3 = commandValidator.calculateValidator("withdraw 12345678 999");
        boolean actual4 = commandValidator.calculateValidator("withdraw 12345678 1000");
        boolean actual5 = commandValidator.calculateValidator("withdraw 12345678 1001");
        assertFalse(actual);
        assertFalse(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
        assertTrue(actual4);
        assertFalse(actual5);


    }

    @Test
    void bounding_amounts_for_checking() {
        Account checking = new Checking("12345678", 0.6f);
        bank.addAccount(checking.getId(), checking);
        boolean actual = commandValidator.calculateValidator("withdraw 12345678 -1");
        boolean actual1 = commandValidator.calculateValidator("withdraw 12345678 0");
        boolean actual2 = commandValidator.calculateValidator("withdraw 12345678 1");
        boolean actual3 = commandValidator.calculateValidator("withdraw 12345678 399");
        boolean actual4 = commandValidator.calculateValidator("withdraw 12345678 400");
        boolean actual5 = commandValidator.calculateValidator("withdraw 12345678 401");
        assertFalse(actual);
        assertFalse(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
        assertTrue(actual4);
        assertFalse(actual5);
    }

    @Test
    void two_withdraws_savings_fails() {
        Savings savings = new Savings("12345678", 0.6f);
        bank.addAccount(savings.getId(), savings);
        boolean actual = commandValidator.calculateValidator("withdraw 12345678 100");
        savings.setLastWithdrawMonth();
        boolean actual1 = commandValidator.calculateValidator("withdraw 12345678 1");
        assertTrue(actual);
        assertFalse(actual1);
    }

    @Test
    void withdraw_from_cd_before_and_after_twelve_months() {
        CD cd = new CD("12345678", 0.6f, 1001);
        bank.addAccount(cd.getId(), cd);
        boolean actual = commandValidator.calculateValidator("withdraw 12345678 1001");
        commandProcessor.calculateProcessor("pass 12");
        boolean actual1 = commandValidator.calculateValidator("withdraw 12345678 1030");
        assertFalse(actual);
        assertTrue(actual1);
    }


}

