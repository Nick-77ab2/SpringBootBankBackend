package com.example.my_spring_boot_app;

public class CreateCommandValidator extends CommandValidator {

    public CreateCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean validate(String command) {
        String[] splitCommand = command.split(" ");
        if (boundsParser(splitCommand)) {
            if (checkingSavingsBoundsChecker(splitCommand)) {
                return true;
            } else if (splitCommand[1].toLowerCase().equals("cd") && splitCommand.length == 5) {
                return cdBoundsChecker(splitCommand);
            }
        }
        return false;
    }

    public boolean boundsParser(String[] splitCommand) {
        boolean atLeastStandardLength = splitCommand.length > 3;
        if (atLeastStandardLength) {
            return idBounds(splitCommand) && isFloat(splitCommand[3]) && Float.parseFloat(splitCommand[3]) <= 10;
        }
        return false;
    }

    public boolean checkingSavingsBoundsChecker(String[] splitCommand) {
        return (splitCommand[1].toLowerCase().equals("checking") || splitCommand[1].toLowerCase().equals("savings")) && splitCommand.length == 4;

    }

    public boolean cdBoundsChecker(String[] splitCommand) {
        if (splitCommand.length == 5) {
            return isDouble(splitCommand[4]) && Double.parseDouble(splitCommand[4]) >= 1000 && Double.parseDouble(splitCommand[4]) <= 10000;
        }
        return false;
    }

    public boolean idBounds(String[] splitCommand) {
        return !idExists(splitCommand[2]) && isIDRightLength(splitCommand[2]) && isInteger(splitCommand[2]);
    }
}
