package com.example.my_spring_boot_app;

public class DepositCommandProcessor extends CommandProcessor {
    public DepositCommandProcessor(Bank bank) {
        super(bank);
    }

    public void processInput(String[] splitInput) {
        splitInput = removeCommandFromInput(splitInput);
        String id = splitInput[0];
        String amount = splitInput[1];
        bank.deposit(id, amount);
    }

}
