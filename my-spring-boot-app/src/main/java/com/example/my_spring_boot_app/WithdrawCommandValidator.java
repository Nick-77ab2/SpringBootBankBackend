package com.example.my_spring_boot_app;

import java.math.BigDecimal;

public class WithdrawCommandValidator extends CommandValidator {

    protected WithdrawCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean validate(String command) {
        String[] splitCommand = command.split(" ");
        if (boundsParser(splitCommand)) {
            switch (bank.getAccounts().get(splitCommand[1]).type) {
                case "savings":
                    return savingsBoundsChecker(splitCommand);
                case "checking":
                    return checkingBoundsChecker(splitCommand);
                case "cd":
                    return cdBoundsChecker(splitCommand);
            }
        }
        return false;
    }

    public boolean boundsParser(String[] splitCommand) {
        int commandLength = splitCommand.length;
        if (commandLength == 3) {
            String id = splitCommand[1];
            String withdrawAmount = splitCommand[2];
            return bank.accountExistsByID(id) && isDouble(withdrawAmount);
        }
        return false;
    }

    public boolean savingsBoundsChecker(String[] splitCommand) {
        String id = splitCommand[1];
        double withdrawAmount = Double.parseDouble(splitCommand[2]);
        Savings currentAccount = (Savings) bank.getAccounts().get(id);
        int currentMonth = PassTime.getCurrentTime();
        return withdrawAmount <= 1000 && withdrawAmount > 0 && currentAccount.getLastWithdrawMonth() != currentMonth;
    }

    public boolean checkingBoundsChecker(String[] splitCommand) {
        double withdrawAmount = Double.parseDouble(splitCommand[2]);
        return withdrawAmount <= 400 && withdrawAmount > 0;
    }

    public boolean cdBoundsChecker(String[] splitCommand) {
        String id = splitCommand[1];
        String withdrawAmount = splitCommand[2];
        CD currentAccount = (CD) bank.getAccounts().get(id);
        int currentMonth = PassTime.getCurrentTime();
        return new BigDecimal(withdrawAmount).compareTo(currentAccount.getBalance()) >= 0 && (currentAccount.getCreationMonth() + 12) <= currentMonth;
    }

}
