package com.example.my_spring_boot_app;

public class PassTimeValidator extends CommandValidator {
    public PassTimeValidator(Bank bank) {
        super(bank);
    }

    public boolean validate(String command) {
        command = command.toLowerCase();
        String[] splitCommand = command.split(" ");
        if (lengthTester(splitCommand)) {
            return isInteger(splitCommand[1]) && bounder(splitCommand[1]);
        }
        return false;
    }

    private boolean lengthTester(String[] command) {
        return command.length == 2;
    }

    private boolean bounder(String command) {
        return Integer.parseInt(command) >= 1 && Integer.parseInt(command) <= 60;
    }
}

