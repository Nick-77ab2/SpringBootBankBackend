package com.example.my_spring_boot_app;

public class DepositCommandValidator extends CommandValidator {

    public DepositCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean validate(String command) {
        String[] splitCommand = command.split(" ");
        if (boundsParser(splitCommand)) {
            if (bank.getAccounts().get(splitCommand[1]).type.equals("savings")) {
                return savingsBoundsChecker(splitCommand);
            } else if (bank.getAccounts().get(splitCommand[1]).type.equals("checking")) {
                return checkingBoundsChecker(splitCommand);
            }
        }
        return false;
    }

    public boolean boundsParser(String[] splitCommand) {
        if (checkCommandLength(splitCommand)) {
            return idBounds(splitCommand);
        }

        return false;
    }

    public boolean idBounds(String[] splitCommand) {
        return idExists(splitCommand[1]);
    }

    public boolean savingsBoundsChecker(String[] splitCommand) {
        if (isDouble(splitCommand[2])) {
            return Double.parseDouble(splitCommand[2]) <= 2500 && Double.parseDouble(splitCommand[2]) >= 0;
        }
        return false;
    }

    public boolean checkingBoundsChecker(String[] splitCommand) {
        if (isDouble(splitCommand[2])) {
            return Double.parseDouble(splitCommand[2]) <= 1000 && Double.parseDouble(splitCommand[2]) >= 0;
        }
        return false;
    }

    public boolean checkCommandLength(String[] splitCommand) {
        return splitCommand.length == 3;
    }
}
