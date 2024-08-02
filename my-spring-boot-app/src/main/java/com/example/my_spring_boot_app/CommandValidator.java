package com.example.my_spring_boot_app;

public class CommandValidator {
    protected Bank bank;


    protected CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean calculateValidator(String command) {
        CreateCommandValidator createCommandValidator = new CreateCommandValidator(bank);
        DepositCommandValidator depositCommandValidator = new DepositCommandValidator(bank);
        WithdrawCommandValidator withdrawCommandValidator = new WithdrawCommandValidator(bank);
        PassTimeValidator passTimeValidator = new PassTimeValidator(bank);
        String[] splitCommand = command.split(" ");
        String commandType = splitCommand[0].toLowerCase();
        return switch (commandType) {
            case "create" -> createCommandValidator.validate(command);
            case "deposit" -> depositCommandValidator.validate(command);
            case "withdraw" -> withdrawCommandValidator.validate(command);
            case "pass" -> passTimeValidator.validate(command);
            default -> false;
        };
    }

    public boolean isIDRightLength(String command) {
        return command.length() == 8;
    }

    public boolean idExists(String command) {
        return bank.accountExistsByID(command);
    }

    public boolean isInteger(String input) {
        try {
            Integer.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isFloat(String input) {
        try {
            Float.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDouble(String input) {
        try {
            Double.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
