package com.example.my_spring_boot_app;

import java.util.Arrays;

public class CommandProcessor {
    protected Bank bank;

    protected CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void calculateProcessor(String input) {
        CreateCommandProcessor createCommandProcessor = new CreateCommandProcessor(bank);
        DepositCommandProcessor depositCommandProcessor = new DepositCommandProcessor(bank);
        WithdrawCommandProcessor withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
        PassTimeCommandProcessor passTimeCommandProcessor = new PassTimeCommandProcessor(bank);
        String[] splitInput = input.split(" ");
        String commandType = splitInput[0].toLowerCase();
        switch (commandType) {
            case "create":
                createCommandProcessor.processInput(splitInput);
                break;
            case "deposit":
                depositCommandProcessor.processInput(splitInput);
                break;
            case "withdraw":
                withdrawCommandProcessor.processInput(splitInput);
                break;
            default:
                passTimeCommandProcessor.processInput(splitInput);
                break;
        }
    }

    protected String[] removeCommandFromInput(String[] splitInput) {
        splitInput = Arrays.copyOfRange(splitInput, 1, splitInput.length);
        return splitInput;
    }
}
