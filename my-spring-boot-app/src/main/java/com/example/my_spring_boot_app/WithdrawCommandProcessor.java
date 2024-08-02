package com.example.my_spring_boot_app;

public class WithdrawCommandProcessor extends CommandProcessor {
    public WithdrawCommandProcessor(Bank bank) {
        super(bank);
    }

    public void processInput(String[] splitInput) {
        splitInput = removeCommandFromInput(splitInput);
        String id = splitInput[0];
        String amount = splitInput[1];
        bank.withdraw(id, amount);
    }
}
